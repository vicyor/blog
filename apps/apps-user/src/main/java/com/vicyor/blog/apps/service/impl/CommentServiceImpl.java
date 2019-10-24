package com.vicyor.blog.apps.service.impl;

import com.vicyor.blog.apps.domain.Comment;
import com.vicyor.blog.apps.domain.EsBlog;
import com.vicyor.blog.apps.domain.ReplyComment;
import com.vicyor.blog.apps.repository.CommentRepository;
import com.vicyor.blog.apps.repository.EsBlogRepository;
import com.vicyor.blog.apps.repository.ReplyCommentRepository;
import com.vicyor.blog.apps.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    EsBlogRepository esBlogRepository;
    @Override
    public List<Object> ownComments(String username) {
        List<Comment> comments = commentRepository.findCommentsByUsernameEqualsOrderByCdateDesc(username);
        comments.stream().forEach(comment -> {
            String blogId = comment.getBlogId();
            Optional<EsBlog> optional = esBlogRepository.findById(blogId);
            comment.setBlog(optional.get());
        });
        List<ReplyComment> replyComments = replyCommentRepository.findByFromEqualsOrderByCdateDesc(username);
        replyComments.stream().forEach(replyComment -> {
            String commentId = replyComment.getParentCommentId();
            Optional<Comment> optional = commentRepository.findById(commentId);
            Comment comment = optional.get();
            replyComment.setComment(comment);
            String blogId = comment.getBlogId();
            Optional<EsBlog> blogOp = esBlogRepository.findById(blogId);
            comment.setBlog(blogOp.get());
        });
        List result = new ArrayList();
        result.addAll(comments);
        result.addAll(replyComments);
        return result;
    }
}
