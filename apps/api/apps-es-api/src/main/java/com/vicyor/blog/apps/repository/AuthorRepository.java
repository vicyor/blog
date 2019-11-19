package com.vicyor.blog.apps.repository;

import com.vicyor.blog.apps.domain.Author;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 作者:姚克威
 * 时间:2019/11/19 21:19
 **/
public interface AuthorRepository extends ElasticsearchRepository<Author,String> {
}
