package com.vicyor.blog.apps.config.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者:姚克威
 * 时间:2019/9/17 23:15
 **/

/**
 * 只处理控制器的异常,不处理像filter,4开头的异常
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String, Object> handlerException(Exception e) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", 500);
        result.put("reason", e.getMessage());
        return result;
    }
}
