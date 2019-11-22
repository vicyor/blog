package com.vicyor.blog.apps;

/**
 * 作者:姚克威
 * 时间:2019/11/22 12:02
 **/
interface SuperClass<T>{
    T method(T param);
    String virtual();
}