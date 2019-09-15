package com.vicyor.blog.apps.blog.controller;

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

    @RequestMapping("/delete")
    public String deleteAll() {
        repository.deleteAll();
        elasticsearchTemplate.deleteIndex("blog");
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
                .field("fielddata", true)
                .field("format", "custom")
                .field("pattern", "yyyy-MM-dd hh:mm:ss")
                .endObject()
                .startObject("udate")
                .field("type", "date")
                .field("fielddata", true)
                .field("format", "custom")
                .field("pattern", "yyyy-MM-dd hh:mm:ss")
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
}
