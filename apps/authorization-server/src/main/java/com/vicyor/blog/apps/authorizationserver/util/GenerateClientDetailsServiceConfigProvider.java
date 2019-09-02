package com.vicyor.blog.apps.authorizationserver.util;

import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 作者:姚克威
 * 时间:2019/9/2 20:16
 **/
@Component
public class GenerateClientDetailsServiceConfigProvider implements ClientDetailsServiceConfigProvider {
    @Override
    public void config(InMemoryClientDetailsServiceBuilder builder, Map<String, String> clientProperties) {
        for (Map.Entry<String, String> entry : clientProperties.entrySet()) {
            String clientId = entry.getKey();
            String clientSecret = entry.getValue();
            builder
                    .withClient(clientId).secret(clientSecret)
                    .authorizedGrantTypes("authorization_code", "refresh_token")
                    .scopes("all");
        }
    }
}
