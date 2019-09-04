package com.vicyor.blog.apps.blog.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 作者:姚克威
 * 时间:2019/9/4 0:26
 **/
@WebFilter(urlPatterns = "/*", filterName = "ServletContextPathFilter")
public class ServletContextPathFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //添加项目路径
        request.setAttribute("path",request.getContextPath());
        chain.doFilter(request, response);
    }
}
