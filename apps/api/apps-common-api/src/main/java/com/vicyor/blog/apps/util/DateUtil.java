package com.vicyor.blog.apps.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者:姚克威
 * 时间:2019/9/10 12:19
 **/
public class DateUtil {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }
}
