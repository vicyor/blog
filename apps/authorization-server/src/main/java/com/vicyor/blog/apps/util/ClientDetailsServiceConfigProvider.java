package com.vicyor.blog.apps.util;

import com.vicyor.blog.apps.pojo.OauthClientDetail;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;

import java.util.Map;

public interface ClientDetailsServiceConfigProvider {
    void config(InMemoryClientDetailsServiceBuilder builder, Map<String, OauthClientDetail> clientProperties);
}
