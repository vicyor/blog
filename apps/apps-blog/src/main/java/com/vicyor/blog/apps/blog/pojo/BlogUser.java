package com.vicyor.blog.apps.blog.pojo;

import lombok.Data;

import java.util.List;

/**
 * 作者:姚克威
 * 时间:2019/9/4 22:27
 **/
@Data
public class BlogUser {
    private String username;
    private String name;
    private String job;
    private String motto;
    private String image_uri;
    List<SimpleGrantedAuthority> authorities;
}
