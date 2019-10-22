package com.vicyor.blog.apps.pojo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 作者:姚克威
 * 时间:2019/9/4 22:27
 **/
@Data
public class BlogUser {
    private String username;
    private String password;
    private String name;
    private String job="无业人士";
    private String motto="我的座右铭是没有座右铭";
    private String imageUri = "images/default.jpeg";
    private Collection<? extends GrantedAuthority> authorities;
    private boolean login = false;
}
