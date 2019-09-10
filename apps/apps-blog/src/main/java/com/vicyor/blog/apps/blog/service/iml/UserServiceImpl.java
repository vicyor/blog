package com.vicyor.blog.apps.blog.service.iml;

import com.vicyor.blog.apps.blog.mapper.UserMapper;
import com.vicyor.blog.apps.blog.pojo.BlogUser;
import com.vicyor.blog.apps.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

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

        String username = authentication instanceof AnonymousAuthenticationToken ? "vicyor" : authentication.getPrincipal().toString();
        BlogUser blogUser = userMapper.findBlogUser(username);
        Collection authorities = new ArrayList() {{
            add("ROLE_USER");
            add("ROLE_ADMIN");
        }};
        Collection<? extends GrantedAuthority> authority = authentication instanceof AnonymousAuthenticationToken ? authorities : authentication.getAuthorities();
        blogUser.setAuthorities(authority);
        return blogUser;
    }
}
