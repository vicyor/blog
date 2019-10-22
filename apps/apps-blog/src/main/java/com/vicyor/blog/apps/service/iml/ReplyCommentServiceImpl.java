package com.vicyor.blog.apps.service.iml;

import com.vicyor.blog.apps.domain.ReplyComment;
import com.vicyor.blog.apps.repository.ReplyCommentRepository;
import com.vicyor.blog.apps.service.ReplyCommentService;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 作者:姚克威
 * 时间:2019/10/4 8:47
 **/
@Service
public class ReplyCommentServiceImpl implements ReplyCommentService {
    @Autowired
    ReplyCommentRepository repository;
    @Autowired
    ElasticsearchTemplate template;

    @Override
    public void deleteCommentsByParentCommentId(String commentId) {
        DeleteQuery deleteQuery = new DeleteQuery();
        MatchQueryBuilder builder = QueryBuilders.matchQuery("parentCommentId", commentId);
        deleteQuery.setQuery(builder);
        deleteQuery.setIndex("reply-comment");
        deleteQuery.setType("reply-comment");
        template.delete(deleteQuery);
    }

    @Override
    public ReplyComment createReplyComment(ReplyComment replyComment) {
        ReplyComment comment = repository.index(replyComment);
        return comment;
    }

    @Override
    public List<ReplyComment> getCommentsByParentCommentId(String parentCommentId) {
        TermQueryBuilder builder = QueryBuilders.termQuery("parentCommentId", parentCommentId);
        SearchQuery query = new NativeSearchQuery(builder);
        query.addSort(Sort.by(Sort.Direction.ASC, "cdate"));
        List<ReplyComment> replyComments = template.queryForList(query, ReplyComment.class);
        return replyComments;
    }

    @Override
    public void deletCommentsByCommentId(String commentId) {
        repository.deleteById(commentId);
    }

    @Override
    public Long findReplyCommentCountByParentCommentId(String parentCommentId) {
        Long count = repository.countByParentCommentIdEquals(parentCommentId);
        return count;
    }
}
