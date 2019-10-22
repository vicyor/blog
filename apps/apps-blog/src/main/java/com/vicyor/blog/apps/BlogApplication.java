package com.vicyor.blog.apps;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 作者:姚克威
 * 时间:2019/9/2 11:33
 **/
@EnableCaching
@SpringBootApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy
@MapperScan(basePackages = "com.vicyor.blog.apps.mapper")
@ServletComponentScan(basePackages = "com.vicyor.blog.apps.filter")
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class);
    }
}
