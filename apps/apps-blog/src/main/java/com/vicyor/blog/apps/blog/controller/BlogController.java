package com.vicyor.blog.apps.blog.controller;

import com.vicyor.blog.apps.blog.domain.EsBlog;
import com.vicyor.blog.apps.blog.repository.EsBlogRepository;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:姚克威
 * 时间:2019/9/2 14:50
 **/
@RequestMapping("/blogs")
@Controller
public class BlogController {
    @Autowired
    EsBlogRepository esBlogRepository;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 根据关键字获取blog
     * 分页查询
     */
    @ResponseBody
    @GetMapping
    public List listBlogs(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") int pageSize
    ) {
        List result = new ArrayList();
        List blogs = null;
        long length = 0;
        System.out.println(String.format("keyword=%s,page=%d,pagesize=%d",keyword,page,pageSize));
        if (keyword.equals("")) {
            Sort sort = new Sort(Sort.Direction.DESC, "udate");
            SearchQuery query = new NativeSearchQuery(QueryBuilders.boolQuery());
            query.addIndices("blog");
            query.addTypes("blog");
            query.addSort(sort);
            query.setPageable(PageRequest.of(page, pageSize));
            AggregatedPage<EsBlog> esBlogs = elasticsearchTemplate.queryForPage(query, EsBlog.class);
            length = esBlogs.getTotalElements();
            blogs = esBlogs.getContent();
        } else {
            Page<EsBlog> blogPage = esBlogRepository.findDistinctEsBlogByContentMatchesOrTitleMatchesOrTagMatches(keyword, keyword, keyword, PageRequest.of(page, pageSize));
            length = blogPage.getTotalElements();
            blogs = blogPage.getContent();
        }
        result.add(length);
        result.add(blogs);
        return result;
    }

    /**
     * 根据字段排行获取博客
     */
    @ResponseBody
    @GetMapping("/rank/{field}")
    public List<EsBlog> listBlogsBySort(@PathVariable("field") String field) {
        Sort sort = new Sort(Sort.Direction.DESC, field);
        SearchQuery query = new NativeSearchQuery(QueryBuilders.boolQuery());
        query.addSort(sort);
        query.addIndices("blog");
        query.addTypes("blog");
        query.setPageable(PageRequest.of(0, 10));
        List<EsBlog> blogs = elasticsearchTemplate.queryForList(query, EsBlog.class);
        return blogs;
    }

    @ResponseBody
    @GetMapping("/tag")
    public List listBlogsByTag(
            @RequestParam(value = "tag", defaultValue = "", required = false) String tag,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pagesize", defaultValue = "10", required = false) int pagesize
    ) {
        System.out.println(String.format("tag=%s,page=%d,pagesize=%d",tag,page,pagesize));
        //term查询
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("tag", tag);
        SearchQuery searchQuery = new NativeSearchQuery(matchQuery);
        searchQuery.setPageable(PageRequest.of(page, pagesize));
        searchQuery.addIndices("blog");
        searchQuery.addTypes("blog");
        AggregatedPage<EsBlog> aggregatedPage = elasticsearchTemplate.queryForPage(searchQuery, EsBlog.class);
        List result = new ArrayList();
        result.add(aggregatedPage.getTotalElements());
        result.add(aggregatedPage.getContent());
        return result;
    }
}
