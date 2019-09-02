package domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * 作者:姚克威
 * 时间:2019/9/2 11:41
 **/
@Data
@Document(indexName = "blog", type = "blog")
public class EsBlog implements Serializable {
    private static final long serialVersionUID = 2329388461684078122L;
    @Id //主键
    private String id;
    //博客标题
    private String title;
    //摘要
    private String summary;
    //内容
    private String content;

    public EsBlog(String id, String title, String summary, String content) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.content = content;
    }

    @Override
    public String toString() {
        return "EsBlog{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
