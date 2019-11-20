package com.vicyor.blog.apps;

import com.vicyor.blog.apps.domain.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 作者:姚克威
 * 时间:2019/9/2 11:33
 **/
@EnableCaching
@SpringBootApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy
@MapperScan(basePackages = "com.vicyor.blog.apps.mapper")
@ServletComponentScan(basePackages = "com.vicyor.blog.apps.filter")
public class BlogApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(BlogApplication.class);
        ElasticsearchTemplate template = applicationContext.getBean(ElasticsearchTemplate.class);
        template.putMapping(EsBlog.class);
        template.putMapping(Author.class);
        template.putMapping(Comment.class);
        template.putMapping(ReplyComment.class);
        template.putMapping(Tag.class);
    }
}
