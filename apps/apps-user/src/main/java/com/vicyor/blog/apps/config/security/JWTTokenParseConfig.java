package com.vicyor.blog.apps.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class JWTTokenParseConfig {
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() throws Exception {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        return tokenServices;
    }

    @Bean
    public TokenStore tokenStore() {
        TokenStore tokenStore = new JwtTokenStore(jwtAccessTokenConverter());
        return tokenStore;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //singkey相当于盐
        converter.setSigningKey("blog");
        return converter;
    }
}
