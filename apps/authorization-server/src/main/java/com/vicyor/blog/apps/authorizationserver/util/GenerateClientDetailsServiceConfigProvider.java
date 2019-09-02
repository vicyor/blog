package com.vicyor.blog.apps.authorizationserver.util;

import com.vicyor.blog.apps.authorizationserver.pojo.OauthClientDetail;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 作者:姚克威
 * 时间:2019/9/2 20:16
 **/
@Component
public class GenerateClientDetailsServiceConfigProvider implements ClientDetailsServiceConfigProvider {
    @Override
    public void config(InMemoryClientDetailsServiceBuilder builder, Map<String, OauthClientDetail> clientProperties) {
        for (Map.Entry<String, OauthClientDetail> entry : clientProperties.entrySet()) {
            String clientId = entry.getKey();
            OauthClientDetail oauthClientDetail = entry.getValue();
            List<String> authorizedGrantTypes = oauthClientDetail.getAuthorizedGrantTypes();
            String[] types = authorizedGrantTypes.toArray(new String[authorizedGrantTypes.size()]);
            builder
                    .withClient(clientId).secret(oauthClientDetail.getClientSecret())
                    .authorizedGrantTypes(types)
                    //
                    .redirectUris(oauthClientDetail.getRedirectUri())
                    .scopes(oauthClientDetail.getScope());
        }
    }
}
