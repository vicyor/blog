package com.vicyor.blog.apps.properties;

import com.vicyor.blog.apps.pojo.OauthClientDetail;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 作者:姚克威
 * 时间:2019/9/2 19:46
 **/
@Component
@ConfigurationProperties("sso.authorization")
@Data
public class AuthorizationProperties {
    private String signingKey="blog";
    //客户端配置
    private Map<String, OauthClientDetail> clients;
}
