package com.taotao.controller;


import com.taotao.common.pojo.JsonUtils;
import com.taotao.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 存储上传的图片
 */
@Controller
public class PictureController {
    @Autowired
    PictureService service;
    @Autowired
    HttpServletRequest request;


    @RequestMapping("/pic/upload")
    @ResponseBody
    public String pictureUpload(MultipartFile uploadFile){
        String path = request.getSession().getServletContext().getRealPath("image");

        System.out.println(path);
        Map result = service.uploadPiceure(uploadFile,path);
        //为了保证功能的兼容性，需要把Result转换成json格式的字符串。
        String json = JsonUtils.objectToJson(result);
        return json;
    }
}
