package com.vicyor.blog.apps.blog.controller;

import com.vicyor.blog.apps.blog.domain.ReplyComment;
import com.vicyor.blog.apps.blog.log.LogAnnotation;
import com.vicyor.blog.apps.blog.service.ReplyCommentService;
import com.vicyor.blog.apps.blog.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * 作者:姚克威
 * 时间:2019/10/2 8:16
 **/
@Controller
@RequestMapping("/replyComment")
public class ReplyCommentController {
    @Autowired
    ReplyCommentService service;

    /**
     * 创建回复博客
     */
    @LogAnnotation("创建回复博客")
    @PostMapping("/{username}/create")
    @ResponseBody
    @CacheEvict(cacheNames = "replyComments", key = "#replyComment.parentCommentId")
    public void createReplyComment(@PathVariable("username") String username, @RequestBody ReplyComment replyComment) {
        replyComment.setCdate(new Date(System.currentTimeMillis()));
        replyComment.setImage(UserUtil.blogUser().getImageUri());
        service.createReplyComment(replyComment);
    }

    @LogAnnotation("根据parentCommentId获取replyComment")
    @GetMapping("/{parentCommentId}")
    @Cacheable(cacheNames = "replyComments", key = "#parentCommentId")
    @ResponseBody
    public List<ReplyComment> getReplyComment(
            @PathVariable("parentCommentId") String parentCommentId
    ) {
        List<ReplyComment> replyComments = service.getCommentsByParentCommentId(parentCommentId);
        return replyComments;
    }

    @LogAnnotation("根据commentId删除replyComment")
    @DeleteMapping("/{username}/remove/{parentCommentId}/{commentId}")
    @ResponseBody
    @CacheEvict(cacheNames = "replyComments", key = "#parentCommentId")
    public void deleteCommentId(
            @PathVariable("commentId") String commentId,
            @PathVariable("parentCommentId") String parentCommentId
    ) {
        service.deletCommentsByCommentId(commentId);
    }
}
