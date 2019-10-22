package com.vicyor.blog.apps;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 作者:姚克威
 * 时间:2019/9/2 19:21
 **/
@SpringBootApplication
@MapperScan("com.vicyor.blog.apps.mapper")
public class AuthorizationServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class);
    }
}
