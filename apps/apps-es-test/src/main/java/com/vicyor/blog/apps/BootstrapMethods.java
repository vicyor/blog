package com.vicyor.blog.apps;

import java.util.concurrent.Callable;

/**
 * 作者:姚克威
 * 时间:2019/11/22 12:33
 **/
public class BootstrapMethods {
    public static void main(String[] args) {
        Runnable run=()->{};
        Callable call=()->{
            return "hello world";
        };
    }
}
