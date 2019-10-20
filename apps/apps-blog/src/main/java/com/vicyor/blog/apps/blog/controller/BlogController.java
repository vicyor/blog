package com.vicyor.blog.apps.blog.controller;

import com.vicyor.blog.apps.blog.domain.EsBlog;
import com.vicyor.blog.apps.blog.log.LogAnnotation;
import com.vicyor.blog.apps.blog.pojo.BlogUser;
import com.vicyor.blog.apps.blog.service.BlogService;
import com.vicyor.blog.apps.blog.service.CommentService;
import com.vicyor.blog.apps.blog.service.ReplyCommentService;
import com.vicyor.blog.apps.blog.service.UserService;
import com.vicyor.blog.apps.blog.util.TransformUtil;
import com.vicyor.blog.apps.blog.util.UserUtil;
import com.vicyor.blog.apps.blog.vo.GenerateViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * 作者:姚克威
 * 时间:2019/9/2 14:50
 **/
@RequestMapping("/blogs")
@Controller
public class BlogController {
    @Autowired
    UserService userService;
    @Autowired
    BlogService blogService;
    @Autowired
    CommentService commentService;
    @Autowired
    ReplyCommentService replyCommentService;

    /**
     * 根据关键字获取blog
     * 分页查询
     */
    @ResponseBody
    @GetMapping
    @LogAnnotation("按关键字分页查询所有博客")
    @Cacheable(cacheNames = "blogs", key = "#keyword")
    public GenerateViewObject listBlogs(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") int pageSize
    ) {
        GenerateViewObject viewObject = new GenerateViewObject();
        List blogs = null;
        long length = 0;
        Page<EsBlog> blogPage = blogService.listBlogs(keyword, keyword, keyword, PageRequest.of(page, pageSize));
        length = blogPage.getTotalElements();
        blogs = blogPage.getContent();
        viewObject.put("blogs", blogs);
        viewObject.put("length", length);
        return viewObject;
    }

    /**
     * 根据字段排行获取博客
     */
    @ResponseBody
    @LogAnnotation("根据博客浏览量获取排名前10的博客")
    @GetMapping("/rank/{field}")
    @Cacheable(cacheNames = "blogs", key = "#field")
    public List<EsBlog> listBlogsBySort(@PathVariable("field") String field) {
        List<EsBlog> blogs = blogService.listBlogsBySort(field);
        return blogs;
    }

    /**
     * 根据tag查询
     */
    @ResponseBody
    @LogAnnotation("按博客标签去查询所有的博客")
    @GetMapping("/tag")
    @Cacheable(cacheNames = "blogs", key = "#tag")
    public GenerateViewObject listBlogsByTag(
            @RequestParam(value = "tag", defaultValue = "", required = false) String tag,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pagesize", defaultValue = "10", required = false) int pagesize
    ) {
        GenerateViewObject viewObject = new GenerateViewObject();
        AggregatedPage<EsBlog> aggregatedPage = blogService.listBlogsByTag(tag, page, pagesize);
        viewObject.put("length", aggregatedPage.getTotalElements());
        viewObject.put("blogs", aggregatedPage.getContent());
        return viewObject;
    }

    @LogAnnotation("前往blog浏览页")
    @GetMapping("/{author}/article/{blogId}")
    public String article(HttpServletRequest request,
                          @PathVariable("blogId") String blogId,
                          @PathVariable("author") String author
    ) throws Exception {
        request.setAttribute("blogId", blogId);
        request.setAttribute("author", author);
        //sidebar about-author
        BlogUser blogAuthor = userService.findBlogUser(author);
        request.setAttribute("blogAuthor", blogAuthor);
        EsBlog article = blogService.getArticle(blogId);
        request.setAttribute("tag",article.getTag());
        return "article";
    }

    @LogAnnotation("ajax获取博客")
    @ResponseBody
    @GetMapping("/{author}/article/get/{blogId}")
    @Cacheable(cacheNames = "blog", key = "#blogId")
    public EsBlog getArticle(
            @PathVariable("author") String author,
            @PathVariable("blogId") String blogId
    ) {
        EsBlog blog = blogService.getArticle(blogId);
        return blog;
    }

    @PostMapping("/count/{id}")
    @ResponseBody
    @LogAnnotation("更新博客浏览量")
    public void addVisterCount(@PathVariable("id") String id
    ) throws Exception {
        blogService.addVisterCount(id);
    }

    @LogAnnotation("前往博客更新页")
    @GetMapping("/{author}/update/{id}")
    public String updateBlog(
            @PathVariable("id") String id,
            HttpServletRequest request,
            @PathVariable("author") String author
    ) throws Exception {
        EsBlog blog = blogService.getArticle(id);
        request.setAttribute("blog", TransformUtil.transferObjToMap(blog));
        return "update";
    }

    @LogAnnotation("保存博客的修改内容")
    @PostMapping("/{author}/save/{id}")
    @CacheEvict(cacheNames = "blogs", allEntries = true)
    @CachePut(cacheNames = "blog", key = "#id")
    @ResponseBody
    public void updateArticle(@RequestParam(value = "content", required = false) String content,
                              @RequestParam(value = "title", required = false) String title,
                              @RequestParam(value = "summary", required = false) String summary,
                              @PathVariable(value = "id", required = true) String id,
                              @PathVariable("author") String author
    ) throws Exception {
        blogService.updateBlog(content, title, summary, id, author);
    }

    @LogAnnotation("前往博客新建页")
    @GetMapping("/{username}/new")
    public String newBlog(
            @PathVariable("username") String username
    ) {
        return "createBlog";
    }

    @LogAnnotation("保存新建的博客")
    @PostMapping("/{username}/new")
    @CacheEvict(cacheNames = "blogs", allEntries = true)
    @ResponseBody
    public String createBlog(
            @RequestBody Map<String, String> requestParams,
            @PathVariable("username") String username
    ) {
        String title = requestParams.get("title");
        String content = requestParams.get("content");
        String summary = requestParams.get("summary");
        String tag = requestParams.get("tag");
        //blog图片为用户头像
        EsBlog blog = new EsBlog(title, tag, content, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 1, UserUtil.blogUser().getImageUri(), summary, username);
        blog = blogService.saveBlog(blog);
        return blog.getId();
    }


    @LogAnnotation("删除博客")
    @RequestMapping("/{author}/delete/{id}")
    @CacheEvict(cacheNames = "blogs", allEntries = true)
    @ResponseBody
    public void deleteBlog(@PathVariable("id") String id,
                           @PathVariable("author") String author
    ) {
        //删除博客
        blogService.deleteBlog(id);
        //删除评论

    }
}
