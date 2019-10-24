package com.vicyor.blog.apps.config.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 作者:姚克威
 * 时间:2019/10/24 18:59
 **/
@Configuration
public class HttpConfiguration {
    @Bean
    public RestTemplate restTemplate(){
        RestTemplate template=new RestTemplate();
        List<HttpMessageConverter<?>> converters = template.getMessageConverters();

        return template;
    }
}
