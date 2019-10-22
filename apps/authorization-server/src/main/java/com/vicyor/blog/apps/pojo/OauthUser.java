package com.vicyor.blog.apps.pojo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 作者:姚克威
 * 时间:2019/9/2 23:24
 **/
@Data
public class OauthUser {
    private String username;
    private String password;
    private byte accountNonExpired;
    private byte accountNonLocked;
    private byte credentialsNonExpired;
    private byte enabled;

}
