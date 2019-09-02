package com.vicyor.blog.apps.blog.mapper;

import com.github.pagehelper.Page;
import com.vicyor.blog.api.blog.domain.EsBlog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 作者:姚克威
 * 时间:2019/9/2 12:00
 **/
@Mapper
public interface EsBlogMapper {
    Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(String title, String summary, String content);
}
