package com.vicyor.blog.apps.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2SsoDefaultConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 作者:姚克威
 * 时间:2019/9/2 11:33
 **/
@SpringBootApplication()
@ServletComponentScan(basePackages = "com.vicyor.blog.apps.blog.filter")
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class);
    }
}
