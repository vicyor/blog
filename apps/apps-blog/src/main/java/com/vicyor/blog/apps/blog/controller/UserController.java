package com.vicyor.blog.apps.blog.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 作者:姚克威
 * 时间:2019/9/3 0:50
 **/
@RequestMapping("/user")
@RestController
public class UserController {
    @GetMapping
    public Authentication user(Authentication authentication)
    {
        return authentication;
    }
}
