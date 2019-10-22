package com.vicyor.blog.apps.blog.controller;

import com.vicyor.blog.apps.blog.repository.CommentRepository;
import com.vicyor.blog.apps.blog.repository.EsBlogRepository;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.internal.SearchContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.io.IOException;

/**
 * 作者:姚克威
 * 时间:2019/9/4 21:24
 **/
@RestController
@RequestMapping("/admin")
public class AdminController {

}

