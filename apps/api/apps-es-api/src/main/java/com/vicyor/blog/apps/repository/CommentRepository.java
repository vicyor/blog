package com.vicyor.blog.apps.repository;

import com.vicyor.blog.apps.domain.Comment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * 作者:姚克威
 * 时间:2019/9/15 1:04
 **/
public interface CommentRepository extends ElasticsearchRepository<Comment, String> {
    public List<Comment> findCommentsByBlogIdEqualsOrderByCdate(String blogId);
    public List<Comment> findCommentsByUsernameEqualsOrderByCdateDesc(String username);
}
