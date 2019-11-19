package com.vicyor.blog.apps.domain;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 作者:姚克威
 * 时间:2019/10/22 8:10
 **/
@Data
@Document(indexName = "author",type = "author")
public class Author {
    //对应 mysql数据库User的username
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String author;
    //头像
    private String uri;

    protected Author() {

    }

    public Author(String author, String uri) {
        this.author = author;
        this.uri = uri;
    }
}
