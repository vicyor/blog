package com.vicyor.blog.apps.blog.controller;

import com.vicyor.blog.apps.blog.repository.CommentRepository;
import com.vicyor.blog.apps.blog.repository.EsBlogRepository;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 作者:姚克威
 * 时间:2019/9/4 21:24
 **/
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    EsBlogRepository repository;
    @Autowired
    CommentRepository commentRepository;

    @RequestMapping("/delete")
    public String deleteAll() {
        repository.deleteAll();
        commentRepository.deleteAll();
        elasticsearchTemplate.deleteIndex("blog");
        elasticsearchTemplate.deleteIndex("comment");
        return "删除成功";
    }

    @RequestMapping("/mapping/blog")
    public String mapping() throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("properties")
                .startObject("title")
                .field("type", "text")
                .endObject()
                .startObject("tag")
                .field("type", "keyword")
                .endObject()
                .startObject("id")
                .field("type", "text")
                .endObject()
                .startObject("content")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()
                .startObject("summary")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()
                .startObject("cdate")
                .field("type", "date")
                .field("format", "yyyy-MM-dd HH:mm:ss")
                .endObject()
                .startObject("udate")
                .field("type", "date")
                .field("format", "yyyy-MM-dd HH:mm:ss")
                .endObject()
                .startObject("count")
                .field("type", "long")
                .endObject()
                .startObject("uri")
                .field("type", "keyword")
                .endObject()
                .startObject("author")
                .field("type", "keyword")
                .endObject()
                .endObject()
                .endObject();

        elasticsearchTemplate.putMapping("blog", "blog", builder);
        return "mapping成功";
    }

    @RequestMapping("/create/blog")
    public String index() {
        elasticsearchTemplate.createIndex("blog");
        return "创建index成功";
    }

    @RequestMapping("/create/comment")
    public String indexComment() {
        elasticsearchTemplate.createIndex("comment");
        return "创建index成功";
    }

    @RequestMapping("/mapping/comment")
    public String mappingComment() throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("properties")
                .startObject("id")
                .field("type", "text")
                .endObject()
                .startObject("content")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()
                .startObject("username")
                .field("type", "keyword")
                .endObject()
                .startObject("cdate")
                .field("type", "date")
                .field("format", "yyyy-MM-dd hh:mm:ss")
                .endObject()
                .startObject("blogId")
                .field("type", "text")
                .endObject()
                .startObject("image")
                .field("type", "text")
                .endObject()
                .endObject()
                .endObject();

        elasticsearchTemplate.putMapping("comment", "comment", builder);
        return "mapping成功";
    }
}
