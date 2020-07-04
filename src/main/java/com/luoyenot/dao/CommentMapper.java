package com.luoyenot.dao;

import com.luoyenot.domain.Comment;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author luoyenot
 * @create 2020-05-15-9:48
 */
public interface CommentMapper extends Mapper<Comment> {

    List<Comment> selectCommentLimit(int limit);

    int clearTable();

}
