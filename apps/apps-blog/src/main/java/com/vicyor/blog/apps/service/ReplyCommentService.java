package com.vicyor.blog.apps.service;

import com.vicyor.blog.apps.domain.ReplyComment;

import java.util.List;

public interface ReplyCommentService {
    void deleteCommentsByParentCommentId(String commentId);

    ReplyComment createReplyComment(ReplyComment replyComment);

    List<ReplyComment> getCommentsByParentCommentId(String parentCommentId);

    void deletCommentsByCommentId(String commentId);

    Long findReplyCommentCountByParentCommentId(String parentCommentId);
}
