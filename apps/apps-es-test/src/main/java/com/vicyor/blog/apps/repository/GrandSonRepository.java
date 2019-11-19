package com.vicyor.blog.apps.repository;

import com.vicyor.blog.apps.entity.GrandSon;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 作者:姚克威
 * 时间:2019/10/29 21:15
 **/
public interface GrandSonRepository extends ElasticsearchRepository<GrandSon,String> {
}
