package com.vicyor.blog.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 作者:姚克威
 * 时间:2019/10/20 17:45
 **/
@SpringBootApplication
@MapperScan("com.vicyor.blog.apps.blog.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class);
    }
}
