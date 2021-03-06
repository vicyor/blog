package com.vicyor.blog.apps.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * 作者:姚克威
 * 时间:2019/9/2 11:41
 * TODO 修改为父子结构
 **/
@Data
@Document(indexName = "blog", type = "blog")
public class EsBlog implements Serializable {

    private static final long serialVersionUID = 2329388461684078122L;
    @Id //id
    private String id;
    //博客标题
    private String title;
    //内容
    @Field(type = FieldType.Text,
            analyzer = "ik_max_word"
    )
    private String content;
    //总结
    private String summary;
    //创建时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Field(type = FieldType.Date,
            format = DateFormat.custom,
            pattern = "yyyy-MM-dd HH:mm:ss"
    )

    private Date cdate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    //更新时间
    @Field(type = FieldType.Date,
            format = DateFormat.custom,
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date udate;
    //浏览数量
    private long count;
    private Author author;
    private Tag tag;

    public EsBlog(String title, String tag, String content,  Date udate, long count, String uri, String summary, String author) {
        this.title = title;
        this.tag = new Tag(tag);
        this.content = content;
        this.cdate = new Date(System.currentTimeMillis());
        this.udate = udate;
        this.count = count;
        this.author = new Author(author, uri);
        this.summary = summary;
    }

    protected EsBlog() {

    }
    private String tagId;
    private String authorId;
}
