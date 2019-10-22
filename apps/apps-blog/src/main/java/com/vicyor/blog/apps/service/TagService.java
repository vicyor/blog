package com.vicyor.blog.apps.service;

import com.vicyor.blog.apps.domain.Tag;

import java.util.List;

/**
 * 作者:姚克威
 * 时间:2019/10/20 16:39
 **/
public interface TagService {
    List<Tag> findAllTags();
}
