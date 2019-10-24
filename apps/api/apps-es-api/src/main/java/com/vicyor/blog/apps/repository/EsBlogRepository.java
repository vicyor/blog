package com.vicyor.blog.apps.repository;

import com.vicyor.blog.apps.domain.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * 作者:姚克威
 * 时间:2019/9/2 12:48
 **/
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, String> {
    /**
     * match 查询
     */
    Page<EsBlog> findDistinctEsBlogByContentContainingOrTitleContainingOrTag_TagContainingOrderByUdateDesc(String content, String title, String tag, Pageable pageable);

    /**
     * 根据tag.name去查询blog
     */
    Page<EsBlog> findEsBlogByTag_Tag(String tag, Pageable pageable);
    Page<EsBlog> findEsBlogsByAuthor_AuthorEquals(String author,Pageable pageable);
}
