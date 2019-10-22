package com.vicyor.blog.apps.config.springmvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 作者:姚克威
 * 时间:2019/9/11 14:20
 **/
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/up/**")
                .addResourceLocations("file:/upload/");
    }
}
