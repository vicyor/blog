package com.vicyor.blog.apps.blog.controller;

import com.vicyor.blog.api.blog.domain.EsBlog;
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
@RequestMapping("/blog")
@RestController
public class BlogController {
    @Autowired
    EsBlogRepository esBlogRepository;
    @RequestMapping
    public List<EsBlog> blogs(@RequestParam("title") String title, @RequestParam("summary") String summary, @RequestParam("content") String content) {
        Pageable pageable = new PageRequest(0, 10);
        Page<EsBlog> articals = esBlogRepository.findDistinctEsBlogByTitleContainingAndSummaryContainingAndContentContaining(title, summary, content, pageable);
        return articals.getContent();
    }
}
