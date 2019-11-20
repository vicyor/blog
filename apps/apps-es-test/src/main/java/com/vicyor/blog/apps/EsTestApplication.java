package com.vicyor.blog.apps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * 作者:姚克威
 * 时间:2019/10/29 17:57
 **/
//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class EsTestApplication {
    public static void main(String[] args) throws Throwable {
//        ConfigurableApplicationContext applicationContext = SpringApplication.run(EsTestApplication.class);
        MethodHandles.Lookup lookup= MethodHandles.lookup();
        class A{
            private String name;
            public A(String name){
                this.name=name;
            }
            public void say(){
                System.out.println(name);
            }
        }
        A a=new A("abcdefg");
        MethodHandle handle = lookup.findVirtual(a.getClass(),"say",MethodType.methodType(void.class)).bindTo(a);
        handle.invokeExact();
    }
}
