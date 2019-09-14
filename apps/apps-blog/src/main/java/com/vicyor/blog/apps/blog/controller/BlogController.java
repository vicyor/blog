package com.vicyor.blog.apps.blog.controller;

import com.vicyor.blog.apps.blog.domain.EsBlog;
import com.vicyor.blog.apps.blog.repository.EsBlogRepository;
import com.vicyor.blog.apps.blog.util.DateUtil;
import com.vicyor.blog.apps.blog.util.TransformUtil;
import com.vicyor.blog.apps.blog.util.UserUtil;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.*;

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
            Page<EsBlog> blogPage = esBlogRepository.findDistinctEsBlogByContentMatchesOrTitleMatchesOrTagMatchesOrderByUdateDesc(keyword, keyword, keyword, PageRequest.of(page, pageSize));
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

    /**
     * 根据tag查询
     */
    @ResponseBody
    @GetMapping("/tag")
    public List listBlogsByTag(
            @RequestParam(value = "tag", defaultValue = "", required = false) String tag,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pagesize", defaultValue = "10", required = false) int pagesize
    ) {
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

    @GetMapping("/article/{id}")
    public String article(HttpServletRequest request,
                          @PathVariable("id") String id
    ) throws Exception {
        GetQuery getQuery = new GetQuery();
        getQuery.setId(id);
        EsBlog blog = elasticsearchTemplate.queryForObject(getQuery, EsBlog.class);
        request.setAttribute("blog", TransformUtil.transferObjToMap(blog));
        return "article";
    }

    @PostMapping("/count/{id}")
    @ResponseBody
    public void addVisterCount(@PathVariable("id") String id,
                               @RequestParam("count") int count
    ) throws Exception {
        UpdateQuery query = new UpdateQueryBuilder()
                .withIndexName("blog")
                .withType("blog")
                .withId(id)
                .build();
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.doc(XContentFactory
                .jsonBuilder()
                .startObject()
                .field("count", count + 1)
                .endObject()
        );
        query.setUpdateRequest(updateRequest);
        elasticsearchTemplate.update(query);
    }

    @GetMapping("/update/{id}")
    public String updateBlog(
            @PathVariable("id") String id,
            HttpServletRequest request
    ) throws Exception {
        IdsQueryBuilder queryBuilder = QueryBuilders.idsQuery().addIds(id);
        GetQuery query = new GetQuery();
        query.setId(id);
        EsBlog blog = elasticsearchTemplate.queryForObject(query, EsBlog.class);
        request.setAttribute("blog", TransformUtil.transferObjToMap(blog));
        return "update";
    }

    @PostMapping("/save/{id}")
    @ResponseBody
    public void updateArticle(@RequestParam(value = "content", required = false) String content,
                              @RequestParam(value = "title", required = false) String title,
                              @RequestParam(value = "summary", required = false) String summary,
                              @PathVariable(value = "id", required = true) String id,
                              HttpServletRequest request
    ) throws Exception {
        UpdateRequest updateRequest = new UpdateRequest();
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder().startObject();
        Optional.ofNullable(content).ifPresent(con -> {
            try {
                xContentBuilder.field("content", con);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Optional.ofNullable(title).ifPresent(tit -> {
            try {
                xContentBuilder.field("title", tit);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Optional.ofNullable(summary).ifPresent(sum -> {
            try {
                xContentBuilder.field("summary", sum);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        xContentBuilder.field("udate", DateUtil.formatDate(new Date()));
        xContentBuilder.endObject();
        updateRequest.doc(xContentBuilder);
        UpdateQuery query = new UpdateQuery();
        query.setUpdateRequest(updateRequest);
        query.setId(id);
        query.setIndexName("blog");
        query.setType("blog");
        elasticsearchTemplate.update(query);
    }

    @GetMapping("/new")
    public String newBlog() {
        return "createBlog";
    }

    @PostMapping("/new")
    @ResponseBody
    public String createBlog(@RequestBody Map<String, String> requestParams) {
        String title = requestParams.get("title");
        String content = requestParams.get("content");
        String summary = requestParams.get("summary");
        String tag = requestParams.get("tag");
        //blog图片为用户头像
        EsBlog blog = new EsBlog(title, tag, content, new Date(), new Date(), 1, UserUtil.blogUser().getImageUri(), summary, UserUtil.blogUser().getUsername());
        IndexQuery query = new IndexQuery();
        query.setObject(blog);
        query.setIndexName("blog");
        query.setType("blog");
        return elasticsearchTemplate.index(query);
    }

    @RequestMapping("/delete/{id}")
    public String deleteBlog(@PathVariable("id") String id) {
        DeleteQuery query = new DeleteQuery();
        query.setIndex("blog");
        query.setType("blog");
        query.setQuery(QueryBuilders.idsQuery().addIds(id));
        elasticsearchTemplate.delete(query);
        return "redirect:/index";
    }
}
