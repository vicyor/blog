package com.vicyor.blog.apps.authorizationserver.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 作者:姚克威
 * 时间:2019/9/2 20:35
 **/
@Component
@Data
@ConfigurationProperties("sso.security")
public class WebSecurityProperties {
    //post
    private String authorizationUri="/login";
    //get
    private String loginUri="/login";
}
