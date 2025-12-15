package com.itheima.controller;

import com.itheima.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class uploadController {

    @PostMapping("/upload")

    public Result upload(String name,String age,MultipartFile file) throws IOException {
        log.info("接收参数 ： {}{}{}",name,age,file);

        //获取原始文件名
        String originalFilename=file.getOriginalFilename();
        //新文件名
        String suffix=originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString()+suffix;
        file.transferTo(new File("/Users/wangjingtao/aaa/"+newFileName));
        return Result.success();
    }
}
