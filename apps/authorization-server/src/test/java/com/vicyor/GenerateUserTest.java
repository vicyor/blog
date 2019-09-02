package com.vicyor;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 作者:姚克威
 * 时间:2019/9/3 0:55
 **/
public class GenerateUserTest {
    @Test
    public void generatePassword(){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String encode = encoder.encode("123456");
        System.out.println(encode);
    }
}
