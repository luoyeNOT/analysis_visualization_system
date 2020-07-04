package com.luoyenot.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author luoyenot
 * @create 2020-05-18-9:06
 */

public class CleanMapperImpl extends Mapper<LongWritable, Text, Text, NullWritable> {

    Text text = new Text();
//    public static Text transformTextToUTF8(Text text, String encoding) {
//        String value = null;
//        try {
//            value = new String(text.getBytes(), 0, text.getLength(), encoding);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return new Text(value);
//    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

//        Text next=transformTextToUTF8(value,"GBK");
//        String data = next.toString();
        String data = value.toString();

        //解析json数据
        JSONObject jsonObject = JSONObject.parseObject(data);

        JSONObject json = jsonObject.getJSONObject("json");


        if(!data.contains("comments")) {
            return;
        }

        JSONArray comments = json.getJSONArray("comments");

        for (int i = 0; i < comments.size(); i++) {
            JSONObject comment = comments.getJSONObject(i);
            String content = comment.getString("content").replace('\n', ' ');
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(content);
            stringBuilder.append("\t");
            String result = stringBuilder.toString();
            text.set(result);
            context.write(text, NullWritable.get());
        }


    }

}
