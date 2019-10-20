package com.vicyor.blog.apps.blog.repository;

import com.vicyor.blog.apps.blog.domain.EsBlog;
import com.vicyor.blog.apps.blog.domain.ReplyComment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 作者:姚克威
 * 时间:2019/10/4 9:14
 **/
public interface ReplyCommentRepository extends ElasticsearchRepository<ReplyComment, String> {
    Long countByParentCommentIdEquals(String parentCommentId);
}
