package com.vicyor.blog.apps.blog.controller;

import com.vicyor.blog.apps.blog.pojo.BlogUser;
import com.vicyor.blog.apps.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/regester")
    @ResponseBody
    public Map regesterUser(@RequestBody BlogUser blogUser) {
        Map map = new HashMap();
        try {
            userService.saveUser(blogUser);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 500);
            map.put("result", e.getCause().toString());
        }
        return map;
    }
}
