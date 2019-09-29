package com.vicyor.blog.apps.blog.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 作者:姚克威
 * 时间:2019/9/29 11:00
 **/
@Data
public class GenerateViewObject implements Serializable {
    private static final long serialVersionUID = -2222585386164750838L;
    private Map<String,Object> params;
    public void put(String key,Object value){
        params.put(key,value);
    }
}
