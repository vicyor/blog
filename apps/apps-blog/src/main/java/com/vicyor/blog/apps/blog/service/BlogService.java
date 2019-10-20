package com.vicyor.blog.apps.blog.service;

import com.vicyor.blog.apps.blog.domain.EsBlog;
import com.vicyor.blog.apps.blog.vo.GenerateViewObject;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {
    GenerateViewObject listBlogs(String content, String title, String tag, Pageable pageable);

    List<EsBlog> listTop10BlogsByOrderByCountDesc();

    GenerateViewObject listBlogsByTag(String tag, int page, int pageSize);

    EsBlog getArticle(String blogId);

    void addVisterCount(String id);

    void updateBlog(String content, String title, String summary, String id, String author) throws Exception;

    EsBlog saveBlog(EsBlog blog);

    void deleteBlog(String id);
}
