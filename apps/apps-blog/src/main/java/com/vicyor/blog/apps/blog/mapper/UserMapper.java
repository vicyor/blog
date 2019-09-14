package com.vicyor.blog.apps.blog.mapper;

import com.vicyor.blog.apps.blog.pojo.BlogUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 作者:姚克威
 * 时间:2019/9/4 22:22
 **/
@Mapper
public interface UserMapper {
    public BlogUser findBlogUser(@Param("username") String username);
    public List<String> findRole(@Param("username")String username);
}
