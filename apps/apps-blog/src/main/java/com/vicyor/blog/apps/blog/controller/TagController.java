package com.vicyor.blog.apps.blog.controller;

import com.vicyor.blog.apps.blog.domain.Tag;
import com.vicyor.blog.apps.blog.service.TagService.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 作者:姚克威
 * 时间:2019/10/20 16:38
 **/
@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping("/listAllTags")
    public List<Tag> findAllTags() {
        return tagService.findAllTags();
    }
}
