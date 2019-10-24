package com.vicyor.blog.apps;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 作者:姚克威
 * 时间:2019/10/20 17:45
 **/
@SpringBootApplication
@EnableAspectJAutoProxy
@MapperScan("com.vicyor.blog.apps.mapper")
@ServletComponentScan("com.vicyor.blog.apps.filter")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class);
    }
}
