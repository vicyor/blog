package com.vicyor.blog.apps.blog.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

/**
 * 标签类
 **/
@Data
@Document(indexName = "tag", type = "tag")
public class Tag implements Serializable {
    protected Tag() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @Field(
            type = FieldType.Keyword
    )
    private String tag;

    public Tag(String tag) {
        this.tag = tag;
    }
}
