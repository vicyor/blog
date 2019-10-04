package com.vicyor.blog.apps.blog.service;

import com.vicyor.blog.apps.blog.domain.ReplyComment;

import java.util.List;

public interface ReplyCommentService {
    void deleteCommentsByParentCommentId(String commentId);

    ReplyComment createReplyComment(ReplyComment replyComment);

    List<ReplyComment> getCommentsByParentCommentId(String parentCommentId);

    void deletCommentsByCommentId(String commentId);
}
