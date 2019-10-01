package com.vicyor.blog.apps.blog.controller;

import com.vicyor.blog.apps.blog.domain.Comment;
import com.vicyor.blog.apps.blog.log.LogAnnotation;
import com.vicyor.blog.apps.blog.pojo.BlogUser;
import com.vicyor.blog.apps.blog.repository.CommentRepository;
import com.vicyor.blog.apps.blog.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 作者:姚克威
 * 时间:2019/9/15 1:14
 **/
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    ElasticsearchTemplate template;
    @Autowired
    CommentRepository repository;

    @ResponseBody
    @PostMapping("/{blogId}/save")
    @LogAnnotation("保存个人评论")
    @CacheEvict(cacheNames = "comments", key = "#params.get('blogId')", allEntries = true)
    public Comment saveComment(
            @RequestBody Map<String, String> params,
            @PathVariable("blogId") String blogId
    ) {
        String content = params.get("content");
        BlogUser blogUser = UserUtil.blogUser();
        Comment comment = new Comment(content, UserUtil.blogUser().getUsername(), new Date(), blogId, blogUser.getImageUri());
        comment = repository.index(comment);
        return comment;
    }

    @ResponseBody
    @GetMapping("/{blogId}/list")
    @LogAnnotation("获取博客所有评论")
    @Cacheable(cacheNames = "comments", key = "#blogId")
    public List<Comment> listComments(@PathVariable("blogId") String blogId
    ) {
        List<Comment> comments = repository.findCommentsByBlogIdEquals(blogId);
        return comments;
    }

    @DeleteMapping("/remove/{blogId}/{commentId}")
    @ResponseBody
    @LogAnnotation("删除个人评论")
    @CacheEvict(cacheNames = "comments", key = "#blogId")
    public void deleteComment(
            @PathVariable("commentId") String commentId,
            @PathVariable("blogId") String blogId
    ) {
        repository.deleteById(commentId);
    }
}
