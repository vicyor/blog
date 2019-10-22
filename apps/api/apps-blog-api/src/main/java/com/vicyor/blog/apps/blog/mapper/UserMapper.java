package com.vicyor.blog.apps.blog.mapper;

import com.vicyor.blog.apps.blog.pojo.BlogUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 作者:姚克威
 * 时间:2019/9/4 22:22
 **/
@Mapper
public interface UserMapper {
    BlogUser findBlogUser(@Param("username") String username);
    List<String> findRole(@Param("username") String username);

    void saveUser(BlogUser blogUser);
    void saveUserInfo(BlogUser blogUser);
}
