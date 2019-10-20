package com.vicyor.blog.apps.blog.service.iml;

import com.vicyor.blog.apps.blog.domain.Comment;
import com.vicyor.blog.apps.blog.repository.CommentRepository;
import com.vicyor.blog.apps.blog.service.CommentService;
import com.vicyor.blog.apps.blog.service.ReplyCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 作者:姚克威
 * 时间:2019/9/15 1:21
 **/
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    ElasticsearchTemplate template;
    @Autowired
    CommentRepository repository;

    @Autowired
    ReplyCommentService replyCommentService;
    @Override
    public Comment addComment(Comment comment) {
        return repository.save(comment);
    }

    @Override
    public List<Comment> listComments(String blogId) {
        List<Comment> comments = repository.findCommentsByBlogIdEqualsOrderByCdate(blogId);
        return comments;
    }

    @Override
    public void deleteComment(String commentId) {
        repository.deleteById(commentId);
        //删除子评F论
        replyCommentService.deleteCommentsByParentCommentId(commentId);
    }
}
