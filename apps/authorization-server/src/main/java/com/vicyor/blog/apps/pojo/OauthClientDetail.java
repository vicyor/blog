package com.vicyor.blog.apps.pojo;

import lombok.Data;

import java.util.List;

/**
 * 作者:姚克威
 * 时间:2019/9/3 1:19
 **/
@Data
public class OauthClientDetail {
    private String clientId;
    private String clientSecret;
    private String scope = "all";
    private List<String> authorizedGrantTypes;
    private List<String> redirectUri;
}
