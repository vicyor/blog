package com.vicyor.blog.apps.blog.filter;

import com.vicyor.blog.apps.blog.pojo.BlogUser;
import com.vicyor.blog.apps.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 作者:姚克威
 * 时间:2019/9/4 0:26
 **/
@WebFilter(urlPatterns = "/*", filterName = "ServletContextPathFilter")
@Component
public class ServletContextPathFilter extends HttpFilter {
    @Autowired
    private UserService userService;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //添加项目路径
        request.setAttribute("path", request.getContextPath());
        String uri = request.getRequestURL().toString();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HttpSession session = request.getSession();

        if (authentication instanceof AnonymousAuthenticationToken) {
            if (session.getAttribute("user") == null) {
                BlogUser blogUser = userService.findBlogUser(authentication);
                session.setAttribute("user", blogUser);
            }
        }
        if (!(uri.contains("css") || uri.contains("html") || uri.contains("images") || uri.contains("js"))) {
            //添加认证信息
            if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
                request.setAttribute("authentication", authentication);
                //在会话中添加用户信息
                if (session.getAttribute("user") == null) {
                    BlogUser blogUser = userService.findBlogUser(authentication);
                    session.setAttribute("user", blogUser);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
