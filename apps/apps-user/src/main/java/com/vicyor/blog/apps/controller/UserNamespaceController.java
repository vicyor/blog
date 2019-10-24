package com.vicyor.blog.apps.controller;

import com.vicyor.blog.apps.domain.Comment;
import com.vicyor.blog.apps.domain.EsBlog;
import com.vicyor.blog.apps.pojo.BlogUser;
import com.vicyor.blog.apps.service.BlogService;
import com.vicyor.blog.apps.service.CommentService;
import com.vicyor.blog.apps.service.UserService;
import com.vicyor.blog.apps.util.UserUtil;
import com.vicyor.blog.apps.vo.GenerateViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 作者:姚克威
 * 时间:2019/10/23 8:19
 **/
@Controller
@RequestMapping("/{username}")
public class UserNamespaceController {
    @Autowired
    UserService userService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    BlogService blogService;
    @Autowired
    CommentService commentService;
    @GetMapping("/modify")
    public String toModifyPage(@PathVariable("username") String username) {
        return "modify";
    }

    @PostMapping("/modify")
    @ResponseBody
    public void modifyUser(@RequestBody BlogUser user, HttpSession session) {
        BlogUser dbUser = userService.findBlogUser(user.getUsername());
        dbUser.setJob(user.getJob());
        dbUser.setName(user.getName());
        dbUser.setUsername(user.getUsername());
        dbUser.setMotto(user.getMotto());
        userService.updateBlogUser(dbUser);
        //更新下会话
        session.setAttribute("user", dbUser);
    }

    @RequestMapping("/head")
    @ResponseBody
    public Map uploadHeadImg(MultipartFile file, HttpSession session) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.setContentType(MediaType.parseMediaType("multipart/form-data; charset=UTF-8"));
        //只有通过path写绝对路径了
        URI uri = URI.create("file:///tmp/" + file.getOriginalFilename());
        Path p = Paths.get(uri);
        file.transferTo(p);
        File tf = p.toFile();
        FileSystemResource resource = new FileSystemResource(tf);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("editormd-image-file", resource);
        String upLoadUri = "http://127.0.0.1/blog/file/upload";
        //删除文件
        Map<String, Object> result = restTemplate.postForObject(upLoadUri, params, Map.class);
        tf.delete();
        String url = result.get("url").toString();
        BlogUser user = (BlogUser) session.getAttribute("user");
        user.setImageUri(url.substring(6)); //去掉开头的/blog/
        session.setAttribute("user", user);
        userService.updateBlogUser(user);
        return result;
    }

    @GetMapping("/myblog")
    public String toMyBlog() {
        return "myblog";
    }

    @GetMapping("/blogs")
    @ResponseBody
    public GenerateViewObject ownedBlogs(@RequestParam("page") int page, @RequestParam("pagesize") int pagesize) {
        BlogUser user = UserUtil.blogUser();
        Page<EsBlog> pg = blogService.queryOwnedBlogs(user.getUsername(), page, pagesize);
        GenerateViewObject object = new GenerateViewObject();
        object.put("length", pg.getTotalElements());
        object.put("blogs", pg.get().collect(Collectors.toList()));
        return object;
    }
    @GetMapping("/toComment")
    @ResponseBody
    public String toComment(){
        return "comment";
    }
    @GetMapping("/comment")
    @ResponseBody
    public List<Comment>ownComments(){
        BlogUser blogUser = UserUtil.blogUser();
        List<Object>comments=commentService.ownComments(blogUser.getUsername());
        return null;
    }
}
