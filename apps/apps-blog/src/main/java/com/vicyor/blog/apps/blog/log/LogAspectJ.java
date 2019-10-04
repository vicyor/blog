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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

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
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        LogAnnotation annotation = method.getAnnotation(LogAnnotation.class);
        String content = annotation.value();
        if (content == null || content.trim().length() == 0) {
            content = method.getName();
        }
        log.info(String.format("%s  开始", this.new Log(url, ip, classMethod, args, content)));
        Object proceed = joinPoint.proceed();
        return proceed;
    }

    private class Log {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;
        private String content;

        public Log(String url, String ip, String classMethod, Object[] args, String content) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
            this.content = content;
        }

        @Override
        public String toString() {
            return "Log{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
}
