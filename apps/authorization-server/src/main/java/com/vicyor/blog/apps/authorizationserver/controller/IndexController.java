package com.vicyor.blog.apps.authorizationserver.controller;

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
public class IndexController {
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
    public Map regesterUser(@RequestBody Map params) {
        Map response= restTemplate.postForObject("http://127.0.0.1/blog/regester", params, HashMap.class);
        return response;
    }
}
