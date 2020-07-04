package com.luoyenot.service.impl;

import com.luoyenot.dao.CommentMapper;
import com.luoyenot.domain.Comment;
import com.luoyenot.service.CommentService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * @author luoyenot
 * @create 2020-05-15-10:26
 */
@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    @Qualifier("sqlSessionBatch")
    private SqlSession sqlSessionBatch;


    /**
     * 插入评论
     * */
    @Override
    public boolean insertComment(Comment comment) {

        CommentMapper mapper = sqlSessionBatch.getMapper(CommentMapper.class);

//        int i = commentMapper.insert(comment);
        int i = mapper.insert(comment);
        if(i!=0){
            return true;
        }
        return false;
    }

    /**
     * 获取前x条评论
     * */
    @Override
    public List<Comment> selectCommentLimit(int limit) {
        return commentMapper.selectCommentLimit(limit);
    }

    @Override
    public int clearTable() {
        return commentMapper.clearTable();
    }


    /**
     *获取分词结果
     * 统计词频
     */
    @Override
    public Map getTextDef(String text) throws IOException {
        Map<String, Integer> wordsFren=new HashMap<String, Integer>();
        IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(text), true);
        Lexeme lexeme;
        while ((lexeme = ikSegmenter.next()) != null) {
            if(lexeme.getLexemeText().length()>1){
                if(wordsFren.containsKey(lexeme.getLexemeText())){
                    wordsFren.put(lexeme.getLexemeText(),wordsFren.get(lexeme.getLexemeText())+1);
                }else {
                    wordsFren.put(lexeme.getLexemeText(),1);
                }
            }
        }
        return wordsFren;
    }

    /**
     * 排序
     * */
    @Override
    public List<Map.Entry<String, Integer>> sortSegmentResult(Map<String,Integer> wordsFrenMaps){

        List<Map.Entry<String, Integer>> wordFrenList = new ArrayList<Map.Entry<String, Integer>>(wordsFrenMaps.entrySet());
        Collections.sort(wordFrenList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String, Integer> obj2) {
                return obj2.getValue() - obj1.getValue();
            }
        });

        return wordFrenList;
    }

}
