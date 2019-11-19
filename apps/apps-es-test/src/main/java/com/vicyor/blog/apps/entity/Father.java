package com.vicyor.blog.apps.entity;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.lang.annotation.Documented;

/**
 * 作者:姚克威
 * 时间:2019/10/29 20:54
 * 必须手动区es创建
 * PUT /test
 * {
 *   "mappings":{
 *     "father":{
 *       "properties":{
 *         "name":{"type":"text"},
 *         "son":{
 *           "type":"nested",
 *           "properties":{
 *             "name":{"type":"text"},
 *             "grandson":{
 *               "type":"nested",
 *               "properties":{
 *                 "name":{"type":"text"}
 *               }
 *
 *             }
 *           }
 *         }
 *       }
 *     }
 *   }
 * }
 **/
@Document(indexName = "test",type = "father")
public class Father {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String name;
    protected Father() {
    }
    public Father(String id,String name){
        this.id=id;
        this.name=name;
    }

}
