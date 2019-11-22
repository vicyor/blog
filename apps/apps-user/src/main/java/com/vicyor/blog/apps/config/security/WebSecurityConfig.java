package com.vicyor.blog.apps.config.security;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;

/**
 * 作者:姚克威
 * 时间:2019/10/22 14:19
 **/
@EnableOAuth2Sso
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**/login", "/**/auth", "/**/oauth","/**/*.html","/**/*.js","/**/*.css").permitAll()
                .anyRequest()
                .permitAll()
                .and()
                .csrf().disable()
                .cors().disable()
                .headers().frameOptions().disable();

    }
}
