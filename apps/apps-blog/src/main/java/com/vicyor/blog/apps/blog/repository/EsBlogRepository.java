package com.vicyor.blog.apps.blog.repository;

import com.vicyor.blog.apps.blog.domain.EsBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;


/**
 * 作者:姚克威
 * 时间:2019/9/2 12:48
 **/
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, String> {
   /**
    * match 查询
    */
   List<EsBlog> findDistinctEsBlogByContentMatchesOrTitleMatchesOrTagMatches(String content,String title,String tag);

}
