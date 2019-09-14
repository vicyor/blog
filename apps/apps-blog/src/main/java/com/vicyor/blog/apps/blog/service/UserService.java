package com.vicyor.blog.apps.blog.service;

import com.vicyor.blog.apps.blog.pojo.BlogUser;
import org.springframework.security.core.Authentication;

/**
 * 作者:姚克威
 * 时间:2019/9/4 23:25
 **/
public interface UserService {
    public BlogUser     findBlogUser( Authentication authentication);
}
