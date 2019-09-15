package com.vicyor.blog.apps.blog.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 作者:姚克威
 * 时间:2019/9/15 16:36
 **/
@Slf4j
@Aspect
@Component
public class LogAspectJ {
    @Pointcut("@annotation(com.vicyor.blog.apps.blog.log.LogAnnotation)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation annotation = method.getAnnotation(LogAnnotation.class);
        String content = annotation.value();
        if (content == null || content.trim().length() == 0)    {
            content = method.getName();
        }
        log.info(String.format("%s  开始", content));
        Object proceed = joinPoint.proceed();
        log.info(String.format("%s  结束", content));
        return proceed;
    }
}
