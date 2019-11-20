package com.vicyor.blog.apps.config;

import com.vicyor.blog.apps.handlerinterceptor.BootstrapHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 作者:姚克威
 * 时间:2019/11/20 10:15
 **/
@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {
    @Autowired
    BootstrapHandlerInterceptor handlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handlerInterceptor).addPathPatterns("/**");
    }
}
