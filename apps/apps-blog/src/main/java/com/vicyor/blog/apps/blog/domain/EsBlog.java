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
 * 时间:2019/9/2 11:41
 **/
@Data
@Document(indexName = "blog", type = "blog")
public class EsBlog implements Serializable {
    private static final long serialVersionUID = 2329388461684078122L;
    @Id //id
    private String id;
    //博客标题
    private String title;
    //摘要
    private String tag;
    //内容
    private String content;
    //总结
    private String summary;
    //创建时间
    @Field(type = FieldType.Date,
            index = false,
            format = DateFormat.custom,
            pattern = "yyyy-MM-dd hh:mm:ss"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date cdate;
    //更新时间
    @Field(type = FieldType.Date,
            index = false,
            format = DateFormat.custom,
            pattern = "yyyy-MM-dd hh:mm:ss"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date udate;
    //浏览数量
    private long count;
    //图片uri
    private String uri="images/text02.jpg";
    public EsBlog(String title, String tag, String content,Date cdate,Date udate,long count,String uri,String summary) {
        this.title = title;
        this.tag = tag;
        this.content = content;
        this.cdate=cdate;
        this.udate=udate;
        this.count=count;
        this.uri=uri;
        this.summary=summary;
    }

    protected EsBlog(){

    }
}
