package com.vicyor.blog.apps.authorizationserver.util;

import com.vicyor.blog.apps.authorizationserver.pojo.OauthClientDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 作者:姚克威
 * 时间:2019/9/2 20:18
 **/
@Component
public class ClientDetailsServiceConfigManager {
    @Autowired
    private List<ClientDetailsServiceConfigProvider> providers;

    public void execute(InMemoryClientDetailsServiceBuilder builder, Map<String, OauthClientDetail> clientProperties) {
        providers.stream().forEach(provider -> {
            provider.config(builder, clientProperties);
        });
    }
}
