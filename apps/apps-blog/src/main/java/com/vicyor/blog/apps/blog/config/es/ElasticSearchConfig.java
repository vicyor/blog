package com.vicyor.blog.apps.blog.config.es;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

/**
 * 作者:姚克威
 * 时间:2019/9/10 11:21
 **/
@EnableElasticsearchRepositories(basePackages = "com.vicyor.blog.apps.blog.repository")
@Configuration
@EnableJpaRepositories(basePackages = "com.vicyor.blog.apps.blog.repository")
public class ElasticSearchConfig {
    /**
     * java.lang.IllegalStateException: availableProcessors is already set to [6], rejecting [6]
     */
    @PostConstruct
    void init(){
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
}
