package com.vicyor.blog.apps.blog.repository;

import com.vicyor.blog.api.blog.domain.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;


/**
 * 作者:姚克威
 * 时间:2019/9/2 12:48
 **/
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, String> {
    Page<EsBlog> findDistinctEsBlogByTitleContainingAndSummaryContainingAndContentContaining(String title, String summary, String content, Pageable pageable);
}
