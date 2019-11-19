package com.vicyor.blog.apps;

import com.vicyor.blog.apps.entity.Father;
import com.vicyor.blog.apps.entity.GrandSon;
import com.vicyor.blog.apps.entity.Son;
import com.vicyor.blog.apps.repository.FatherRepository;
import com.vicyor.blog.apps.repository.GrandSonRepository;
import com.vicyor.blog.apps.repository.SonRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 作者:姚克威
 * 时间:2019/10/29 17:57
 **/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class EsTestApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(EsTestApplication.class);
        FatherRepository repository = applicationContext.getBean(FatherRepository.class);
        repository.save(new Father("1","姚克威"));
        SonRepository sonRepository = applicationContext.getBean(SonRepository.class);
        sonRepository.save(new Son("1","姚秀","1"));
        GrandSonRepository grandSonRepository = applicationContext.getBean(GrandSonRepository.class);
        grandSonRepository.save(new GrandSon("1","姚雅","1"));

    }
}
