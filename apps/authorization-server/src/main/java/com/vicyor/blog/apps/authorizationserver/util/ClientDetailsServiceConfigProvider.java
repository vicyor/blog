package com.vicyor.blog.apps.authorizationserver.util;

import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;

import java.util.Map;

public interface ClientDetailsServiceConfigProvider {
    void config(InMemoryClientDetailsServiceBuilder builder, Map<String, String> clientProperties);
}
