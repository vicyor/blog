package com.vicyor.blog.apps.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Parent;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * 作者:姚克威
 * 时间:2019/10/29 21:00
 **/
@Document(type = "grandson",indexName = "test")
public class GrandSon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @Parent(type = "son")
    private String sonId;
    protected GrandSon(){

    }
    private String name;

    public GrandSon(String sonId,String id, String name) {
        this.sonId = sonId;
        this.id=id;
        this.name = name;
    }
}
