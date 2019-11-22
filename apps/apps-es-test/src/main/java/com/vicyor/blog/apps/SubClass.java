package com.vicyor.blog.apps;

/**
 * 作者:姚克威
 * 时间:2019/11/22 11:38
 **/

public class SubClass implements SuperClass<String> {

    @Override
    public String method(String param) {
        System.out.println("hello");
        return param;
    }

    @Override
    public String virtual() {
        return "abc";
    }

    public static void main(String[] args) {
        SuperClass<String> sp = new SubClass();
//        sp.method("abc");
//        sp.method(new Object());
//        sp.method(new Object().toString());
        sp.virtual();
    }

}
