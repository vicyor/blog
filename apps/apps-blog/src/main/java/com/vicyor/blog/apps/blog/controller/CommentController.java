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
    @PostMapping("/save")
    @LogAnnotation("保存评论")
    @CacheEvict(cacheNames = "comments",key = "#params.get('blogId')",allEntries = true)
    public Comment saveComment(@RequestBody Map<String, String> params) {
        String content = params.get("content");
        String blogId = params.get("blogId");
        BlogUser blogUser = UserUtil.blogUser();
        Comment comment = new Comment(content, blogUser.getUsername(), new Date(), blogId,blogUser.getImageUri());
        Comment com = repository.index(comment);
        return com;
    }

    @ResponseBody
    @GetMapping("/list")
    @LogAnnotation("获取评论")
    @Cacheable(cacheNames = "comments",key = "#blogId")
    public List<Comment> listComments(@RequestParam("blogId") String blogId
    ) {
        List<Comment> comments = repository.findCommentsByBlogIdEquals( blogId);
        return comments;
    }
    @DeleteMapping("/remove/{commentId}")
    @ResponseBody
    @LogAnnotation("删除评论")
    @CacheEvict(cacheNames = "comments",allEntries = true)
    public void deleteComment(@PathVariable("commentId") String commentId){
        repository.deleteById(commentId);
    }
}
