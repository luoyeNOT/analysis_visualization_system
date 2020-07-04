package com.luoyenot.service;

import com.luoyenot.domain.Comment;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author luoyenot
 * @create 2020-05-15-10:26
 */
public interface CommentService {



    /**
     * 插入评论
     * */
    boolean insertComment(Comment comment);

    /**
     * 获取前x条评论
     * */
    List<Comment> selectCommentLimit(int limit);

    /**
     * 清除表数据
     * */
    int clearTable();

    /**
     * 获取分词结果
     * */
    Map getTextDef(String text) throws IOException;

    /**
     * 排序
     * */
    List<Map.Entry<String, Integer>> sortSegmentResult(Map<String, Integer> wordsFrenMaps);

}
