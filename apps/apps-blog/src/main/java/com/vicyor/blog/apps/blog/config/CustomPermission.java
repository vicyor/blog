package com.vicyor.blog.apps.blog.config;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * 作者:姚克威
 * 时间:2019/9/30 21:31
 * 将用户名和角色进行绑定
 **/
@Component("customPermission")
public class CustomPermission {
    private static AntPathMatcher antPathMatcher = new AntPathMatcher();

    public static boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        boolean hasPermission = false;
        String username = authentication.getPrincipal().toString();
        String requestUri = request.getRequestURL().toString();
        if(requestUri.contains(username)){
            hasPermission= true;
        }
        return hasPermission;
    }
}
