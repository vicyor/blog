package com.vicyor.blog.apps.blog.util;

import com.vicyor.blog.apps.blog.domain.EsBlog;
import com.vicyor.blog.apps.blog.pojo.BlogUser;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 作者:姚克威
 * 时间:2019/9/13 18:30
 **/
public class UserUtil {
    public static BlogUser blogUser() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        //没有线程安全问题,request就是放在threadlocal中
        HttpServletRequest request = requestAttributes.getRequest();
        Optional<Object> optional = Optional.ofNullable(request.getSession().getAttribute("user"));
        return optional.get() == null ? null : (BlogUser) optional.get();
    }
    /**
     * 判断当前用户是否拥有博客的修改权限
     */
    public static boolean hasBlog(ElasticsearchTemplate template, String blogId) {
        GetQuery query = new GetQuery();
        query.setId(blogId);
        EsBlog blog = template.queryForObject(query, EsBlog.class);
        String author = blog.getAuthor().getAuthor();
        String username = blogUser().getUsername();
        return author.equals(username);
    }



}
