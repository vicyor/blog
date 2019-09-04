package com.vicyor.blog.apps.blog.mapper;

import com.vicyor.blog.apps.blog.pojo.BlogUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 作者:姚克威
 * 时间:2019/9/4 22:22
 **/
@Mapper
public interface UserMapper {
    public BlogUser findBlogUser(@Param("username") String username);
}
