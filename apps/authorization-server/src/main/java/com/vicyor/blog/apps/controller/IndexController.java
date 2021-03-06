package com.vicyor.blog.apps.controller;

import com.vicyor.blog.apps.service.UserService;
import com.vicyor.blog.apps.pojo.BlogUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者:姚克威
 * 时间:2019/9/15 12:31
 **/
@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    UserService userService;
    @GetMapping("/login")
    public String toLogin() {
        return "login";
    }

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/user")
    @ResponseBody
    public String Authentication(Authentication authentication) {
        return authentication.toString();
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
