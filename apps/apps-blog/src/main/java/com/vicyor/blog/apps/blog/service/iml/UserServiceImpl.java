package com.vicyor.blog.apps.blog.service.iml;

import com.vicyor.blog.apps.blog.mapper.UserMapper;
import com.vicyor.blog.apps.blog.pojo.BlogUser;
import com.vicyor.blog.apps.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 作者:姚克威
 * 时间:2019/9/4 23:25
 **/
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public BlogUser findBlogUser(String username, Authentication authentication) {
        BlogUser blogUser = userMapper.findBlogUser(username);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        blogUser.setAuthorities(new ArrayList() {{
            addAll(authorities);
        }});
        return blogUser;
    }
}
