package com.vicyor.blog.apps.blog.service;

import com.vicyor.blog.apps.blog.domain.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;

import java.util.List;

public interface BlogService {
    Page<EsBlog> listBlogs(String content, String title, String tag, Pageable pageable);

    List<EsBlog> listBlogsBySort(String field);

    AggregatedPage<EsBlog> listBlogsByTag(String tag, int page, int pageSize);

    EsBlog getArticle(String blogId);

    void addVisterCount(String id);

    void updateBlog(String content, String title, String summary, String id, String author) throws Exception;

    EsBlog saveBlog(EsBlog blog);

    void deleteBlog(String id);
}
