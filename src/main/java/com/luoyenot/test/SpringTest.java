package com.luoyenot.test;

import com.luoyenot.dao.CommentMapper;
import com.luoyenot.domain.Comment;
import com.luoyenot.domain.Msg;
import com.luoyenot.service.CommentService;
import net.sf.jsqlparser.statement.select.Top;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * @author luoyenot
 * @create 2020-05-15-9:53
 */
public class SpringTest {

    ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/applicationContext.xml");


    @Test
    public void test03() throws IOException {

        long startTime = System.currentTimeMillis();

        SqlSession sqlSession = (SqlSession) ioc.getBean("sqlSessionBatch");
        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);

        CommentService bean = ioc.getBean(CommentService.class);
        File file = new File("D:\\output\\part-r-00000");
        String text = FileUtils.readFileToString(file);
        Map<String,Integer> wordsFrenMaps=bean.getTextDef(text);
        List<Map.Entry<String, Integer>> entries = bean.sortSegmentResult(wordsFrenMaps);
        Comment comment = new Comment();
        for(Map.Entry<String, Integer> m:entries){
            comment.setCommentKey(m.getKey());
            comment.setCommentValue(m.getValue());
//            bean.insertComment(comment);
            commentMapper.insert(comment);
        }
        System.out.println("执行完成");

        System.out.println("********************************* 计时："+
                (System.currentTimeMillis()-startTime));
    }

    @Test
    public void test02(){
        CommentService bean = ioc.getBean(CommentService.class);
        Comment comment = new Comment();
        comment.setCommentKey("测试");
        comment.setCommentValue(100);
        boolean b = bean.insertComment(comment);
        System.out.println(b);
    }

    @Test
    public void test01(){
        CommentMapper bean = ioc.getBean(CommentMapper.class);

        List<Comment> comments = bean.selectCommentLimit(25);
        for(Comment c:comments){
            System.out.println(c);
        }

    }


}
