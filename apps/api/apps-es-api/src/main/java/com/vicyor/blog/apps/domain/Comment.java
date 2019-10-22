package com.vicyor.blog.apps.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.sql.Date;

/**
 * 作者:姚克威
 * 时间:2019/9/15 0:51
 **/
@Data

@Document(indexName = "comment", type = "comment")
public class Comment implements Serializable {
    private static final long serialVersionUID = -706588845757984703L;
    @Id
    private String id;
    @Field(type = FieldType.Text,
            analyzer = "ik_max_word"
    )
    private String content;
    @Field(type = FieldType.Keyword)
    private String username;
    @Field(type = FieldType.Date,
            format = DateFormat.custom,
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cdate;
    private String blogId;
    private String image;

    public Comment(String content, String username, Date cdate, String blogId, String image) {
        this.content = content;
        this.username = username;
        this.cdate = cdate;
        this.blogId = blogId;
        this.image = image;
    }

    /**
     * 生成代理可用到
     */
    protected Comment() {
    }
}
