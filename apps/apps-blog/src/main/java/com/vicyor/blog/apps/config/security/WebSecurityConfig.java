package com.vicyor.blog.apps.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * 作者:姚克威
 * 时间:2019/9/4 10:29
 * 覆盖掉 OAuth2Sso默认的WebSecurityConfig配置
 **/
@EnableOAuth2Sso
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     *
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/auth/**", "/user/**").authenticated()
                //rbac 博客,注解权限,自定义判定方式,会根据用户判断
                .antMatchers("/blog/blogs/**/update/**", "/blog/blogs/**/save/**", "/blog/blogs/**/delete/**", "/blog/comment/**/remove/**")
                .access("@customPermission.hasPermission(request,authentication)")
                //管理员权限才能对es进行操作
                .antMatchers("/blog/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .logout().logoutUrl("/logout").permitAll()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()
        ;
    }
}
