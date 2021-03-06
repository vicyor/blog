package com.vicyor.blog.apps.service;

import com.vicyor.blog.apps.pojo.BlogUser;
import org.springframework.security.core.Authentication;

/**
 * 作者:姚克威
 * 时间:2019/9/4 23:25
 **/
public interface UserService {
    BlogUser findBlogUser(Authentication authentication);

    void saveUser(BlogUser blogUser);

    BlogUser findBlogUser(String username);

    void updateBlogUser(BlogUser dbUser);
}
