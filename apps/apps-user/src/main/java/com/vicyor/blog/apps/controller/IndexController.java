package com.vicyor.blog.apps.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 作者:姚克威
 * 时间:2019/10/22 16:00
 **/
@RequestMapping("/")
@Controller
public class IndexController {
    @GetMapping
    public String root() {

        return "redirect:/index";
    }

    @GetMapping("/index")
    public String toIndex() {
        return "index";
    }

}
