package com.vicyor.blog.apps.blog.controller;

import com.vicyor.blog.apps.blog.domain.ReplyComment;
import com.vicyor.blog.apps.blog.log.LogAnnotation;
import com.vicyor.blog.apps.blog.util.UserUtil;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 作者:姚克威
 * 时间:2019/10/2 8:16
 **/
@Controller
@RequestMapping("/replyComment")
public class ReplyCommentController {
    @Autowired
    private ElasticsearchTemplate template;

    /**
     * 创建回复博客
     */
    @LogAnnotation("创建回复博客")
    @PostMapping("/{username}/create")
    @ResponseBody
    public void createReplyComment(@PathVariable("username") String username, @RequestBody ReplyComment replyComment) {
        replyComment.setCdate(new Date());
        replyComment.setImage(UserUtil.blogUser().getImageUri());
        IndexQuery indexQuery = new IndexQueryBuilder().withIndexName("reply-comment").withType("reply-comment").withObject(replyComment).build();
        String documentId = template.index(indexQuery);
    }

    @LogAnnotation("根据commentId获取replyComment")
    @GetMapping("/{parentCommentId}")
    @ResponseBody
    public List<ReplyComment> getReplyComment(
            @PathVariable("parentCommentId") String parentCommentId
    ) {
        TermQueryBuilder builder = QueryBuilders.termQuery("parentCommentId", parentCommentId);
        SearchQuery query = new NativeSearchQuery(builder);
        List<ReplyComment> replyComments = template.queryForList(query, ReplyComment.class);
        return replyComments;
    }


}
