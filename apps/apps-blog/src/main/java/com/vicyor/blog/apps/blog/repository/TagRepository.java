package com.vicyor.blog.apps.blog.repository;

import com.vicyor.blog.apps.blog.domain.Tag;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

/**
 * 作者:姚克威
 * 时间:2019/10/20 10:57
 **/
public interface TagRepository extends ElasticsearchRepository<Tag,String> {
    Optional<Tag> findByTagEquals(String tagName);
}
