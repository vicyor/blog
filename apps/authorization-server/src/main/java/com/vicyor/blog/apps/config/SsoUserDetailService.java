package com.vicyor.blog.apps.config;

import com.vicyor.blog.apps.pojo.OauthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 作者:姚克威
 * 时间:2019/9/2 21:25
 **/
@Component
public class SsoUserDetailService implements UserDetailsService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private static final String userFieldSql = " username,password,accountNonExpired,accountNonLocked,credentialsNonExpired,enabled ";
    private static final String queryUserSql = "select " + userFieldSql + "from oauth_user where username = ?";
    private static final String queryRoleSql = "select role_name from oauth_role join oauth_user_role on" +
            " oauth_role.role_id=oauth_user_role.role_id and oauth_user_role.username=?";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //获取用户信息
        OauthUser oauthUser = jdbcTemplate.queryForObject(queryUserSql, new Object[]{username}, new BeanPropertyRowMapper<>(OauthUser.class));
        //获取角色信息
        List<String> roles = jdbcTemplate.queryForList(queryRoleSql, new Object[]{username}, String.class);
        List<SimpleGrantedAuthority> authorities = roles.stream().map(authority -> {
            return new SimpleGrantedAuthority("ROLE_"+authority);
        }).collect(Collectors.toList());
        User user = new User(oauthUser.getUsername(), oauthUser.getPassword(), authorities);
        return user;
    }
}
