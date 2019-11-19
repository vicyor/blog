package com.vicyor.blog.apps.entity;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Parent;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * 作者:姚克威
 * 时间:2019/10/29 20:59
 **/
@Document(indexName = "test",type = "son")
public class Son {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @Parent(type = "father")
    private String fatherId;
    protected Son(){

    }
    private String name;

    public Son(String fatherId, String name,String id) {
        this.fatherId = fatherId;
        this.name = name;
        this.id=id;
    }
}
