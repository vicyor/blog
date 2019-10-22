package com.vicyor.blog.apps.blog.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 作者:姚克威
 * 时间:2019/9/13 11:11
 **/
public class TransformUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    public static Map transferObjToMap(Object obj) throws Exception {
        String jsonStr = mapper.writeValueAsString(obj);
        LinkedHashMap linkedHashMap = mapper.readValue(jsonStr, LinkedHashMap.class);
        return linkedHashMap;
    }
}
