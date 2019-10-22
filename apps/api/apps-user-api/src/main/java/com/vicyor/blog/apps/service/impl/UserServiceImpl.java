package com.vicyor.blog.apps.service.impl;

import com.vicyor.blog.apps.mapper.UserMapper;
import com.vicyor.blog.apps.pojo.BlogUser;
import com.vicyor.blog.apps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 作者:姚克威
 * 时间:2019/9/4 23:25
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public BlogUser findBlogUser(Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        //数据库里存匿名用户了
        BlogUser blogUser = userMapper.findBlogUser(username);
        List roles = userMapper.findRole(username);
        Collection authorities = new ArrayList();
        roles.forEach(role -> {
            authorities.add("ROLE_" + role);
        });
        blogUser.setAuthorities(authorities);
        if (! (authentication instanceof AnonymousAuthenticationToken)) {
            blogUser.setLogin(true);
        }
        return blogUser;
    }

    @Override
    public void saveUser(BlogUser blogUser) {
        userMapper.saveUser(blogUser);
        userMapper.saveUserInfo(blogUser);
    }

    @Override
    public BlogUser findBlogUser(String author) {
        return userMapper.findBlogUser(author);
    }


}
