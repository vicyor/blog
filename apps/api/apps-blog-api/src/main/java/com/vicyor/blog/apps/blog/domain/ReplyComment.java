package com.vicyor.blog.apps.blog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * 作者:姚克威
 * 时间:2019/10/1 22:11
 * 回复评论的评论
 **/
@Data

@Document(indexName = "reply-comment", type = "reply-comment")
public class ReplyComment implements Serializable {
    private static final long serialVersionUID = -706588845757984703L;
    @Id
    private String id;
    @Field(type = FieldType.Text,
            analyzer = "ik_max_word"
    )
    private String content;
    //谁回复
    @Field(type = FieldType.Keyword)
    private String from;
    //回复谁
    @Field(type = FieldType.Keyword)
    private String to;
    //最外层评论
    @Field(type = FieldType.Keyword)
    private String parentCommentId;
    @Field(type = FieldType.Date,
            format = DateFormat.custom,
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date cdate;
    private String image;

    public ReplyComment(String content, String from, String to, String parentCommentId, Date cdate, String image) {
        this.content = content;
        this.from = from;
        this.to = to;
        this.parentCommentId = parentCommentId;
        this.cdate = cdate;
        this.image = image;
    }

    /**
     * 生成代理可用到
     */
    protected ReplyComment() {
    }
}
