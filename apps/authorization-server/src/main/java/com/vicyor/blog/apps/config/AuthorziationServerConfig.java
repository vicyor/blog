package com.vicyor.blog.apps.config;

import com.vicyor.blog.apps.pojo.OauthClientDetail;
import com.vicyor.blog.apps.properties.AuthorizationProperties;
import com.vicyor.blog.apps.util.ClientDetailsServiceConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Map;

/**
 * 作者:姚克威
 * 时间:2019/9/2 19:22
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorziationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    AuthorizationProperties authorizationProperties;
    @Autowired
    ClientDetailsServiceConfigManager manager;

    /**
     * 配置token获取方式
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");//isAuthenticated():排除anonymous   isFullyAuthenticated():排除anonymous以及remember-me
    }

    /**
     * 配置oauthclient信息
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        Map<String, OauthClientDetail> clientProperties = authorizationProperties.getClients();
        manager.execute(builder, clientProperties);
    }

    /**
     * 配置token的方式
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(jwtTokenStore())
                .accessTokenConverter(jwtAccessTokenConverter())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST, HttpMethod.GET)
        ;

    }

    /**
     * 配置token 为jwt
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(authorizationProperties.getSigningKey());
        return converter;
    }

    @Autowired

    @Bean
    public TokenStore jwtTokenStore() {
        JwtTokenStore tokenStore = new JwtTokenStore(jwtAccessTokenConverter());


        return tokenStore;
    }
}
