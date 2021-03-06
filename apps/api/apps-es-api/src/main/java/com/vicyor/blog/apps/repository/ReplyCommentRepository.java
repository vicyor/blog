package com.vicyor.blog.apps.repository;

import com.vicyor.blog.apps.domain.ReplyComment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * 作者:姚克威
 * 时间:2019/10/4 9:14
 **/
public interface ReplyCommentRepository extends ElasticsearchRepository<ReplyComment, String> {
    Long countByParentCommentIdEquals(String parentCommentId);
    List<ReplyComment> findByFromEqualsOrderByCdateDesc(String from);
}
