package com.vicyor.blog.apps.controller;

import com.vicyor.blog.apps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 作者:姚克威
 * 时间:2019/9/3 23:11
 **/
@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    UserService userService;

    @GetMapping
    public String root() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String toIndex() {
        return "index";
    }

    @GetMapping("/auth")
    public String auth() {
        //会去认证服务器登录
        return "index";
    }
}
