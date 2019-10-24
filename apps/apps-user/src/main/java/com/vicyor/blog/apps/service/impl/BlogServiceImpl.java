package com.vicyor.blog.apps.service.impl;

import com.vicyor.blog.apps.domain.EsBlog;
import com.vicyor.blog.apps.repository.EsBlogRepository;
import com.vicyor.blog.apps.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 作者:姚克威
 * 时间:2019/10/24 21:39
 **/
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    EsBlogRepository repository;

    @Override
    public Page<EsBlog> queryOwnedBlogs(String author, int page, int pagesize) {
        Page<EsBlog> blogs = repository.
                findEsBlogsByAuthor_AuthorEquals(author, PageRequest.of(page, pagesize));
        return blogs;
    }
}
