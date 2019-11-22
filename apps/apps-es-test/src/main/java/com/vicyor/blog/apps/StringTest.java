package com.vicyor.blog.apps;

import java.util.concurrent.CountDownLatch;

/**
 * 作者:姚克威
 * 时间:2019/11/22 12:10
 **/
public class StringTest {
    //字面量
    private String a="abcdefg";
    private static final CountDownLatch latch=new CountDownLatch(1);
    //ConstantValue
    private static final String b="www";
    private static String c="eeee";
    private final String d="hhhh";
}
