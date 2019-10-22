package com.vicyor.blog.apps.log;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者:姚克威
 * 时间:2019/9/15 16:36
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogAnnotation {

    String value() default "";
    @AliasFor("value")
    String content() default "";
}
