package com.vicyor.blog.apps.service;

import com.vicyor.blog.apps.domain.EsBlog;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 作者:姚克威
 * 时间:2019/10/24 21:38
 **/
public interface BlogService <T>{
    Page<EsBlog> queryOwnedBlogs(String author, int page, int pagesize);
}
