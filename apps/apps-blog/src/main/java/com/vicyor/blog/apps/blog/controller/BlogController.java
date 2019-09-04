package com.vicyor.blog.apps.blog.controller;

import com.vicyor.blog.apps.blog.domain.EsBlog;
import com.vicyor.blog.apps.blog.repository.EsBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     *
     * @param order 默认按照最新排序
     * @param keyword
     * @return
     */
    @RequestMapping
    public List<EsBlog> listBlogs(@RequestParam(value = "order", required = false, defaultValue = "new") String order,
                                  @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword
    ) {
        return null;
    }
}
