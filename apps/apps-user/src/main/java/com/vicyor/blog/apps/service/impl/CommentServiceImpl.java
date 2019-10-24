package com.vicyor.blog.apps.service.impl;

import com.vicyor.blog.apps.domain.Comment;
import com.vicyor.blog.apps.domain.ReplyComment;
import com.vicyor.blog.apps.repository.CommentRepository;
import com.vicyor.blog.apps.repository.ReplyCommentRepository;
import com.vicyor.blog.apps.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 作者:姚克威
 * 时间:2019/10/24 22:16
 **/
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ReplyCommentRepository replyCommentRepository;

    @Override
    public List<Object> ownComments(String username) {
        List<Comment> comments = commentRepository.findCommentsByUsernameEquals(username);
        List<ReplyComment> replyComments = replyCommentRepository.findByFromEquals(username);
        List result = new ArrayList();
        result.addAll(comments);
        result.addAll(replyComments);
        Collections.sort(result, (o1, o2) -> {
            Class<?> c1 = o1.getClass();
            Class<?> c2 = o2.getClass();
            int i = 0;
            try {
                Field d1 = c1.getField("cdate");
                Field d2 = c2.getField("cdate");
                d1.setAccessible(true);
                d2.setAccessible(true);
                Object cdate1 = d1.get(o1);
                Object cdate2 = d2.get(o2);
                //降序排列
                i = ((Date) cdate2).compareTo((Date) cdate1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return i;
        });
        return result;
    }
}
