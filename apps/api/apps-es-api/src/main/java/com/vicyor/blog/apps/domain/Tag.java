package com.vicyor.blog.apps.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Objects;

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
    @Field(type = FieldType.Keyword)
    private String tag;

    public Tag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag1 = (Tag) o;
        return Objects.equals(tag, tag1.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag);
    }
}
