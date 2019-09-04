package com.vicyor.blog.apps.blog.config;

import com.vicyor.blog.api.blog.pojo.BlogUser;
import com.vicyor.blog.apps.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 作者:姚克威
 * 时间:2019/9/4 22:19
 **/
@Component
public class BlogAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    public UserMapper userMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //向session中添加用户信息.
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        BlogUser blogUser = userMapper.findBlogUser(username);

    }
}
