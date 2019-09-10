package com.vicyor.blog.apps.blog.controller;

import com.vicyor.blog.apps.blog.domain.EsBlog;
import com.vicyor.blog.apps.blog.repository.EsBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:姚克威
 * 时间:2019/9/2 14:50
 **/
@RequestMapping("/blogs")
@RestController
public class BlogController {
    @Autowired
    EsBlogRepository esBlogRepository;

    /**
     * 根据关键字获取blog
     */
    @GetMapping
    public List<EsBlog> listBlogs(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword
    ) {
        List<EsBlog> blogs = null;
        if (keyword.equals("")) {
            List<String> fields = new ArrayList<>();
            fields.add("udate");
            Iterable<EsBlog> esBlogs = esBlogRepository.findAll(new Sort(Sort.Direction.DESC, fields));
            blogs = new ArrayList<>();
            for (EsBlog blog : esBlogs) {
                blogs.add(blog);
            }
        } else {
            blogs = esBlogRepository.findDistinctEsBlogByContentMatchesOrTitleMatchesOrTagMatches(keyword, keyword, keyword);
        }

        return blogs;
    }
}
