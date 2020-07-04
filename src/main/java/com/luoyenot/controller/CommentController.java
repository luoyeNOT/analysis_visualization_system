package com.luoyenot.controller;

import com.luoyenot.domain.Comment;
import com.luoyenot.domain.Msg;
import com.luoyenot.service.CommentService;
import com.luoyenot.service.HdfsClient;
import com.luoyenot.service.impl.CleanMapperImpl;
import com.luoyenot.service.impl.CleanReducerImpl;
import com.luoyenot.service.impl.JobProcessorImpl;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author luoyenot
 * @create 2020-05-15-20:56
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private JobProcessorImpl jobProcessor;

    @Autowired
    private HdfsClient hdfsClient;

    /**
     * 数据爬取模块
     */
    @ResponseBody
    @GetMapping("/crawler")
    public Msg crawler(@RequestParam("product_id") Long productId) {
        //爬取数据前先清除文件夹
        String path = "/opt/module/result";//文件夹路径
        String[] cmd = new String[] { "/bin/sh", "-c", "rm -rf "+path };
        try{
            Process process = Runtime.getRuntime().exec(cmd);
        }catch(IOException e){
            e.printStackTrace();
        }
        String outPath = "/opt/module/result";
        String url_start = "https://club.jd.com/comment/skuProductPageComments.action?" +
                "&productId=" + productId + "&score=0&sortType=5&pageSize=10&page=0";
        String url_pattern = "https://club.jd.com/comment/skuProductPageComments.action?" +
                "&productId=" + productId + "&score=0&sortType=5&pageSize=10&page=";
        QueueScheduler scheduler = new QueueScheduler();
        Spider spider = Spider.create(jobProcessor)
                .addUrl(url_start)
                .setScheduler(scheduler)
                .addPipeline(new JsonFilePipeline(outPath))
                .addPipeline(new ConsolePipeline());
        for (int i = 0; i < 100; i++) {
            Request request = new Request();
            request.setUrl(url_pattern + i);
            scheduler.push(request, spider);
        }

        spider.thread(5).run();


        return Msg.success().add("msg", "已完成");

    }

    /**
     *HDFS相关操作
     */
    @ResponseBody
    @PostMapping("/fileUpload")
    public Msg fileUpload() throws IOException, InterruptedException {


        hdfsClient.put();

        return Msg.success().add("msg", "文件上传成功");
    }

    @ResponseBody
    @PostMapping("/fileDownload")
    public Msg fileDownUpload() throws IOException, InterruptedException {

        //下载评论数据集前先清除文件夹
        String path = "/opt/module/output";//文件夹路径
        String[] cmd = new String[] { "/bin/sh", "-c", "rm -rf "+path };
        try{
            Process process = Runtime.getRuntime().exec(cmd);
        }catch(IOException e){
            e.printStackTrace();
        }

        hdfsClient.get();

        return Msg.success().add("msg", "文件下载成功");
    }

    @ResponseBody
    @PostMapping("/fileDelete")
    public Msg fileDeleteUpload() throws IOException, InterruptedException {


        hdfsClient.delete();

        return Msg.success().add("msg", "文件删除成功");
    }

    /**
     * 数据清洗
     */
    @ResponseBody
    @GetMapping("/cleanDriver")
    public Msg cleanDriver() throws IOException, ClassNotFoundException, InterruptedException {

        //1.获取Job实例
        Job job = Job.getInstance(new Configuration());

        //2.设置类路径
        job.setJarByClass(CommentController.class);

        //3.设置Mapper和Reducer
        job.setMapperClass(CleanMapperImpl.class);
        job.setReducerClass(CleanReducerImpl.class);

        //4.设置Mapper和Reducer的输出类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        //5.设置输入输出路径
        FileInputFormat.setInputPaths(job, new Path("hdfs://hadoop102:9000/result/club.jd.com"));
        FileOutputFormat.setOutputPath(job, new Path("hdfs://hadoop102:9000/output"));

        //6.提交Job
        boolean b = job.waitForCompletion(true);
//        System.exit(b ? 0 : 1);

        return Msg.success();

    }

    /**
     * 分词、存入MySQL
     */
    @ResponseBody
    @GetMapping("/dataAnalysis")
    public Msg toIndexPage() throws IOException {

        //清除表数据
        commentService.clearTable();

        //将分词后的数据存入mysql
        File file = new File("/opt/module/output/part-r-00000");
        String text = FileUtils.readFileToString(file, "utf-8");
        Map<String, Integer> wordsFrenMaps = commentService.getTextDef(text);
        List<Map.Entry<String, Integer>> entries = commentService.sortSegmentResult(wordsFrenMaps);
        Comment comment = new Comment();
        for (Map.Entry<String, Integer> m : entries) {
            comment.setCommentKey(m.getKey());
            comment.setCommentValue(m.getValue());
            commentService.insertComment(comment);
        }

        return Msg.success();
    }

    /**
     * 可视化
     */
    @GetMapping("/visualization")
    public String index(HttpServletRequest request) {
        //取出数据 可视化展示
        List<Comment> comments = commentService.selectCommentLimit(25);

        request.setAttribute("commentList", comments);
        return "visualization";
    }


}
