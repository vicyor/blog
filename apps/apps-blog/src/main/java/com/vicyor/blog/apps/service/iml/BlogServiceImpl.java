package com.vicyor.blog.apps.service.iml;

import com.vicyor.blog.apps.domain.Author;
import com.vicyor.blog.apps.domain.Comment;
import com.vicyor.blog.apps.domain.EsBlog;
import com.vicyor.blog.apps.domain.Tag;
import com.vicyor.blog.apps.repository.AuthorRepository;
import com.vicyor.blog.apps.repository.EsBlogRepository;
import com.vicyor.blog.apps.repository.TagRepository;
import com.vicyor.blog.apps.service.BlogService;
import com.vicyor.blog.apps.service.CommentService;
import com.vicyor.blog.apps.vo.GenerateViewObject;
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
    AuthorRepository authorRepository;
    @Autowired
    CommentService commentService;

    @Override
    public GenerateViewObject listBlogs(String content, String title, String tag, Pageable pageable) {
        Page<EsBlog> page = blogRepository.findDistinctEsBlogByContentContainingOrTitleContainingOrTag_TagContainingOrderByUdateDesc(content, title, tag, pageable);
        GenerateViewObject view = new GenerateViewObject();
        view.put("length", page.getTotalElements());
        view.put("blogs", page.get().collect(Collectors.toList()));
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

        blog = blogRepository.save(blog);
    }

    /**
     * 保存博客
     */
    @Override
    public EsBlog saveBlog(EsBlog blog) {
        Tag tag = tagRepository.save(blog.getTag());
        Author author = authorRepository.save(blog.getAuthor());
        blog.setTagId(tag.getId());
        blog.setAuthorId(author.getId());
        return blogRepository.save(blog);
    }

    /**
     * 删除博客
     *
     * @param id
     */
    @Override
    public void deleteBlog(String id) {
        Optional<EsBlog> optional = blogRepository.findById(id);
        //删除blog
        EsBlog blog = optional.get();
        blogRepository.deleteById(id);
        List<Comment> comments = commentService.listComments(id);
        //删除所有评论
        comments.stream().forEach(comment -> {
            commentService.deleteComment(comment.getId());
        });
        //删除tag
        tagRepository.deleteById(blog.getTagId());
        //删除作者
        authorRepository.deleteById(blog.getAuthorId());
    }
}
