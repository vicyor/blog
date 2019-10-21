package com.vicyor.blog.apps.blog.service.iml;

import com.vicyor.blog.apps.blog.domain.Comment;
import com.vicyor.blog.apps.blog.domain.EsBlog;
import com.vicyor.blog.apps.blog.domain.Tag;
import com.vicyor.blog.apps.blog.repository.EsBlogRepository;
import com.vicyor.blog.apps.blog.repository.TagRepository;
import com.vicyor.blog.apps.blog.service.BlogService;
import com.vicyor.blog.apps.blog.service.CommentService;
import com.vicyor.blog.apps.blog.vo.GenerateViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 作者:姚克威
 * 时间:2019/10/4 8:47
 **/
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    EsBlogRepository blogRepository;
    @Autowired
    ElasticsearchTemplate template;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    CommentService commentService;
    @Override
    public GenerateViewObject listBlogs(String content, String title, String tag, Pageable pageable) {
        Page<EsBlog> page = blogRepository.findDistinctEsBlogByContentContainingOrTitleContainingOrTag_TagContainingOrderByUdateDesc(content, title, tag, pageable);
        GenerateViewObject view=new GenerateViewObject();
        view.put("length",page.getTotalElements());
        view.put("blogs",page.get().collect(Collectors.toList()));
        return view;
    }

    @Override
    public List<EsBlog> listTop10BlogsByOrderByCountDesc() {
        Page<EsBlog> page = blogRepository.findAll(PageRequest.of(0, 10, Sort.Direction.DESC, "count"));
        return page.get().collect(Collectors.toList());
    }

    @Override
    public GenerateViewObject listBlogsByTag(String tag, int page, int pageSize) {
        Page<EsBlog> esBlogPage = blogRepository.findEsBlogByTag_Tag(tag, PageRequest.of(page, pageSize));
        GenerateViewObject viewObject = new GenerateViewObject();
        viewObject.put("length", esBlogPage.getTotalElements());
        viewObject.put("blogs", esBlogPage.get().collect(Collectors.toList()));

        return viewObject;
    }

    @Override
    public EsBlog getArticle(String blogId) {
        Optional<EsBlog> optional = blogRepository.findById(blogId);
        return optional.get();
    }

    @Override
    public void addVisterCount(String id) {
        Optional<EsBlog> optional = blogRepository.findById(id);
        EsBlog blog = optional.get();
        blog.setCount(blog.getCount() + 1);
        blogRepository.save(blog);
    }

    /**
     * 更新博客
     */
    @Override
    public void updateBlog(String content, String title, String summary, String id, String author) throws Exception {
        Optional<EsBlog> optional = blogRepository.findById(id);
        EsBlog blog = optional.get();
        if (!StringUtils.isEmpty(content)) {
            blog.setContent(content);
        }
        if (!StringUtils.isEmpty(title)) {
            blog.setTitle(title);
        }
        if (!StringUtils.isEmpty(summary)) {
            blog.setSummary(summary);
        }
        if (!StringUtils.isEmpty(author)) {
            blog.setAuthor(author);
        }
        blog = blogRepository.save(blog);
    }

    /**
     * 保存博客
     */
    @Override
    public EsBlog saveBlog(EsBlog blog) {
        Tag tag = blog.getTag();
        Optional<Tag> optional = tagRepository.findByTagEquals(tag.getTag());
        if (!optional.isPresent()) {
            tag = tagRepository.save(tag);
        } else {
            blog.setTagId(optional.get().getId());
            ;
        }
        return blogRepository.save(blog);
    }

    /**
     * 删除博客
     * @param id
     */
    @Override
    public void deleteBlog(String id) {
        Optional<EsBlog> optional = blogRepository.findById(id);
        //删除blog
        blogRepository.deleteById(id);
        EsBlog blog = optional.get();
        List<Comment> comments = blog.getComments();
        //删除评论
        comments.stream().forEach(comment->{
            commentService.deleteComment(comment.getId());
        });
    }
}
