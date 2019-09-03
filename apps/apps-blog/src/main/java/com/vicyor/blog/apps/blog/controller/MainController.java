package com.vicyor.blog.apps.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 作者:姚克威
 * 时间:2019/9/3 23:11
 **/
@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping
    public String root(){
        return "redirect:/index";
    }
    @GetMapping("/index")
    public String toIndex(){
        return "index";
    }
}
