package com.vicyor.blog.apps.handlerinterceptor;

import com.vicyor.blog.apps.pojo.BlogUser;
import com.vicyor.blog.apps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 作者:姚克威
 * 时间:2019/11/20 10:12
 **/
@Component
public class BootstrapHandlerInterceptor implements HandlerInterceptor
{
    @Autowired
    private UserService userService;
    @Autowired
    private Environment environment;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String applicationName = environment.getProperty("spring.application.name");
        //认证服务器不拦截
        if (applicationName.equals("auth")) {
            return true;
        }
        String profile = environment.getProperty("spring.profiles.active");
        String ip="";
        if(profile.equals("dev")){
            ip="http://127.0.0.1";
        }else if(profile.equals("proc")){
            ip="http://www.vicyor.com";
        }
        request.setAttribute("ip",ip);
        //添加项目路径
        request.setAttribute("path", request.getContextPath());
        String uri = request.getRequestURL().toString();
        HttpSession session = request.getSession();
        if (!(uri.contains("login") || uri.contains("oauth")  || uri.contains("css") || uri.contains("html") || uri.contains("images") || uri.contains("js"))) {
            //认证放在外面的话,服务器在认证时会报错
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            //在会话中添加用户信息
            Object user = session.getAttribute("user");
            if (user == null) {
                //第一次是添加匿名用户到session中
                BlogUser blogUser = userService.findBlogUser(authentication);
                session.setAttribute("user", blogUser);
            } else if (!(authentication instanceof AnonymousAuthenticationToken) && !((BlogUser) user).isLogin()) {
                //登录后更新登录user到session中
                BlogUser blogUser = userService.findBlogUser(authentication);
                session.setAttribute("user", blogUser);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
