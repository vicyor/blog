package com.vicyor.blog.apps.service;

import com.vicyor.blog.apps.domain.Comment;

import java.util.List;

/**
 * 作者:姚克威
 * 时间:2019/9/15 1:21
 **/
public interface CommentService {
    Comment addComment(Comment comment);

    List<Comment> listComments(String blogId);

    void deleteComment(String commentId);
}
