package com.vicyor.blog.apps.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者:姚克威
 * 时间:2019/9/11 13:47
 **/
@RequestMapping("/file")
@Controller
public class FileController {
    @PostMapping("/upload")
    @ResponseBody
    public Map upload(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "editormd-image-file", required = false) MultipartFile attach) throws IOException {
       //文件放在服务器 /upload
        String path = "/upload";
        File directory = new File(path);
        if (!directory.exists())
            directory.mkdir();
        //创建文件
        File file = new File(path + File.separator + attach.getOriginalFilename());
        FileOutputStream fileOutputStream=new FileOutputStream(file);
        InputStream inputStream = attach.getInputStream();
        FileCopyUtils.copy(inputStream,fileOutputStream);
        inputStream.close();
        fileOutputStream.close();
        //客户端可以通过/up访问到upload目录
        Map map = new HashMap();
        map.put("success", 1);
        map.put("message", "上传成功");
        map.put("url", request.getContextPath() + "/up/" + attach.getOriginalFilename());
        return map;
    }
}
