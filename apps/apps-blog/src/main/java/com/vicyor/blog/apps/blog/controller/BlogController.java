package com.vicyor.blog.apps.blog.controller;

import com.vicyor.blog.apps.blog.domain.EsBlog;
import com.vicyor.blog.apps.blog.log.LogAnnotation;
import com.vicyor.blog.apps.blog.repository.EsBlogRepository;
import com.vicyor.blog.apps.blog.util.DateUtil;
import com.vicyor.blog.apps.blog.util.TransformUtil;
import com.vicyor.blog.apps.blog.util.UserUtil;
import com.vicyor.blog.apps.blog.vo.GenerateViewObject;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @LogAnnotation("按关键字分页查询所有博客")
    @Cacheable(cacheNames = "blogs", key = "#keyword")
    public GenerateViewObject listBlogs(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") int pageSize
    ) {
        GenerateViewObject viewObject = new GenerateViewObject();
        List blogs = null;
        long length = 0;
        Page<EsBlog> blogPage = esBlogRepository.findDistinctEsBlogByContentContainingOrTitleContainingOrTagContainingOrderByUdateDesc(keyword, keyword, keyword, PageRequest.of(page, pageSize));
        length = blogPage.getTotalElements();
        blogs = blogPage.getContent();
        viewObject.put("blogs", blogs);
        viewObject.put("length", length);
        return viewObject;
    }

    /**
     * 根据字段排行获取博客
     */
    @ResponseBody
    @LogAnnotation("根据博客浏览量获取排名前10的博客")
    @GetMapping("/rank/{field}")
    @Cacheable(cacheNames = "blogs", key = "#field")
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
    @LogAnnotation("按博客标签去查询所有的博客")
    @GetMapping("/tag")
    @Cacheable(cacheNames = "blogs", key = "#tag")
    public GenerateViewObject listBlogsByTag(
            @RequestParam(value = "tag", defaultValue = "", required = false) String tag,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pagesize", defaultValue = "10", required = false) int pagesize
    ) {
        GenerateViewObject viewObject = new GenerateViewObject();
        //term查询
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("tag", tag);
        SearchQuery searchQuery = new NativeSearchQuery(matchQuery);
        searchQuery.setPageable(PageRequest.of(page, pagesize));
        searchQuery.addIndices("blog");
        searchQuery.addTypes("blog");
        AggregatedPage<EsBlog> aggregatedPage = elasticsearchTemplate.queryForPage(searchQuery, EsBlog.class);
        List result = new ArrayList();
        viewObject.put("length", aggregatedPage.getTotalElements());
        viewObject.put("blogs", aggregatedPage.getContent());
        return viewObject;
    }

    @LogAnnotation("浏览blog")
    @GetMapping("/{author}/article/{id}")
    public String article(HttpServletRequest request,
                          @PathVariable("id") String id,
                          @PathVariable("author") String author
    ) throws Exception {
        GetQuery getQuery = new GetQuery();
        getQuery.setId(id);
        EsBlog blog = elasticsearchTemplate.queryForObject(getQuery, EsBlog.class);
        request.setAttribute("blog", TransformUtil.transferObjToMap(blog));
        return "article";
    }

    @PostMapping("/count/{id}")
    @ResponseBody
    @LogAnnotation("更新博客浏览量")
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

    @LogAnnotation("前往博客更新页")
    @GetMapping("/{author}/update/{id}")
    public String updateBlog(
            @PathVariable("id") String id,
            HttpServletRequest request,
            @PathVariable("author") String author
    ) throws Exception {
        IdsQueryBuilder queryBuilder = QueryBuilders.idsQuery().addIds(id);
        GetQuery query = new GetQuery();
        query.setId(id);
        EsBlog blog = elasticsearchTemplate.queryForObject(query, EsBlog.class);
        request.setAttribute("blog", TransformUtil.transferObjToMap(blog));
        return "update";
    }

    @LogAnnotation("保存博客的修改内容")
    @PostMapping("/{author}/save/{id}")
    @CacheEvict(cacheNames = "blogs", allEntries = true)
    @ResponseBody
    public void updateArticle(@RequestParam(value = "content", required = false) String content,
                              @RequestParam(value = "title", required = false) String title,
                              @RequestParam(value = "summary", required = false) String summary,
                              @PathVariable(value = "id", required = true) String id,
                              HttpServletRequest request,
                              @PathVariable("author") String author
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

    @LogAnnotation("前往博客新建页")
    @GetMapping("/{username}/new")
    public String newBlog(
            @PathVariable("username") String username
    ) {
        return "createBlog";
    }

    @LogAnnotation("保存新建的博客")
    @PostMapping("/{username}/new")
    @CacheEvict(cacheNames = "blogs", allEntries = true)
    @ResponseBody
    public String createBlog(
            @RequestBody Map<String, String> requestParams,
            @PathVariable("username") String username
    ) {
        String title = requestParams.get("title");
        String content = requestParams.get("content");
        String summary = requestParams.get("summary");
        String tag = requestParams.get("tag");
        //blog图片为用户头像
        EsBlog blog = new EsBlog(title, tag, content, new Date(), new Date(), 1, UserUtil.blogUser().getImageUri(), summary, username);
        blog = esBlogRepository.save(blog);
        return blog.getId();
    }


    @LogAnnotation("删除博客")
    @DeleteMapping("/{author}/delete/{id}")
    @CacheEvict(cacheNames = "blogs", allEntries = true)
    @ResponseBody
    public void deleteBlog(@PathVariable("id") String id,
                             @PathVariable("author") String author
    ) {
        DeleteQuery query = new DeleteQuery();
        query.setIndex("blog");
        query.setType("blog");
        query.setQuery(QueryBuilders.idsQuery().addIds(id));
        elasticsearchTemplate.delete(query);
    }
}
