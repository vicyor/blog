package com.vicyor.blog.apps.util;

import com.vicyor.blog.apps.pojo.BlogUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 作者:姚克威
 * 时间:2019/9/13 18:30
 **/
public class UserUtil {
    public static BlogUser blogUser() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        //没有线程安全问题,request就是放在threadlocal中
        HttpServletRequest request = requestAttributes.getRequest();
        Optional<Object> optional = Optional.ofNullable(request.getSession().getAttribute("user"));
        return optional.get() == null ? null : (BlogUser) optional.get();
    }
}
