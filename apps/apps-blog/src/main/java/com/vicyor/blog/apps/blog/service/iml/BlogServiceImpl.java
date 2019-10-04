package com.vicyor.blog.apps.blog.service.iml;

import com.vicyor.blog.apps.blog.domain.EsBlog;
import com.vicyor.blog.apps.blog.repository.EsBlogRepository;
import com.vicyor.blog.apps.blog.service.BlogService;
import com.vicyor.blog.apps.blog.util.DateUtil;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 作者:姚克威
 * 时间:2019/10/4 8:47
 **/
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    EsBlogRepository blogRepository;
    @Autowired
    ElasticsearchTemplate template;

    @Override
    public Page<EsBlog> listBlogs(String content, String title, String tag, Pageable pageable) {
        return blogRepository.findDistinctEsBlogByContentContainingOrTitleContainingOrTagContainingOrderByUdateDesc(content, title, tag, pageable);
    }

    @Override
    public List<EsBlog> listBlogsBySort(String field) {
        Sort sort = new Sort(Sort.Direction.DESC, field);
        SearchQuery query = new NativeSearchQuery(QueryBuilders.boolQuery());
        query.addSort(sort);
        query.addIndices("blog");
        query.addTypes("blog");
        query.setPageable(PageRequest.of(0, 10));
        List<EsBlog> blogs = template.queryForList(query, EsBlog.class);
        return blogs;
    }

    @Override
    public AggregatedPage<EsBlog> listBlogsByTag(String tag, int page, int pageSize) {
        //match查询
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("tag", tag);
        SearchQuery searchQuery = new NativeSearchQuery(matchQuery);
        searchQuery.setPageable(PageRequest.of(page, pageSize));
        searchQuery.addIndices("blog");
        searchQuery.addTypes("blog");
        AggregatedPage<EsBlog> aggregatedPage = template.queryForPage(searchQuery, EsBlog.class);
        return aggregatedPage;
    }

    @Override
    public EsBlog getArticle(String blogId) {
        GetQuery getQuery = new GetQuery();
        getQuery.setId(blogId);
        EsBlog blog = template.queryForObject(getQuery, EsBlog.class);
        return blog;
    }

    @Override
    public void addVisterCount(String id) {
        GetQuery getQuery = new GetQuery();
        getQuery.setId(id);
        EsBlog blog = template.queryForObject(getQuery, EsBlog.class);
        UpdateQuery query = new UpdateQueryBuilder()
                .withIndexName("blog")
                .withType("blog")
                .withId(id)
                .build();
        UpdateRequest updateRequest = new UpdateRequest();
        try {
            updateRequest.doc(XContentFactory
                    .jsonBuilder()
                    .startObject()
                    .field("count", blog.getCount() + 1)
                    .endObject()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        query.setUpdateRequest(updateRequest);
        template.update(query);
    }

    @Override
    public void updateBlog(String content, String title, String summary, String id, String author) throws Exception {
        UpdateRequest updateRequest = new UpdateRequest();
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder().startObject();
        Optional.ofNullable(content).ifPresent(con -> {
            try {
                xContentBuilder.field("content", con);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Optional.ofNullable(title).ifPresent(tit -> {
            try {
                xContentBuilder.field("title", tit);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Optional.ofNullable(summary).ifPresent(sum -> {
            try {
                xContentBuilder.field("summary", sum);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        xContentBuilder.field("udate", DateUtil.formatDate(new Date()));
        xContentBuilder.endObject();
        updateRequest.doc(xContentBuilder);
        UpdateQuery query = new UpdateQuery();
        query.setUpdateRequest(updateRequest);
        query.setId(id);
        query.setIndexName("blog");
        query.setType("blog");
        template.update(query);
    }

    @Override
    public EsBlog saveBlog(EsBlog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public void deleteBlog(String id) {
        blogRepository.deleteById(id);
    }
}
