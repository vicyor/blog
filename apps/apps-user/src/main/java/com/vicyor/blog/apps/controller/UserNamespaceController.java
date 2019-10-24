package com.vicyor.blog.apps.controller;

import com.vicyor.blog.apps.pojo.BlogUser;
import com.vicyor.blog.apps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 作者:姚克威
 * 时间:2019/10/23 8:19
 **/
@Controller
@RequestMapping("/{username}")
public class UserNamespaceController {
    @Autowired
    UserService userService;
    @GetMapping("/modify")
    public String toModifyPage(@PathVariable("username") String username) {
        return "modify";
    }

    @PostMapping("/modify")
    @ResponseBody
    public void modifyUser(@RequestBody BlogUser user, HttpSession session) {
        BlogUser dbUser=userService.findBlogUser(user.getUsername());
        dbUser.setJob(user.getJob());
        dbUser.setName(user.getName());
        dbUser.setUsername(user.getUsername());
        dbUser.setMotto(user.getMotto());
        userService.updateBlogUser(dbUser);
        //更新下会话
        session.setAttribute("user",dbUser);
    }
}
