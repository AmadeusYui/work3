package com.taotao.service.impl;

import com.taotao.service.PictureService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import utils.IDUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片上传服务
 */
@Service
public class PictureServiceImpl implements PictureService {
    @Value("${IMAGE_BASE_URL}")
    private String IMAGE_BASE_URL;

    @Override
    public Map uploadPiceure(MultipartFile uploadFile,String path) {
        Map map = new HashMap();
        if (uploadFile.isEmpty()){
            map.put("error",1);
            map.put("message","存入图片为空");
            return map;
        }
        System.out.println(uploadFile);
        //生成一个新的文件名
        //取原始文件名
        String oldName = uploadFile.getOriginalFilename();
        System.out.println(oldName);
        //生成新文件名
        String newName = IDUtils.getImageName();
        newName = newName + oldName.substring(oldName.lastIndexOf("."));
        System.out.println(newName);
        path = path+"\\"+newName;
        System.out.println(path);
        File file = new File(path);
        try {
            uploadFile.transferTo(file);//把文件保存到本地image文件夹下
            map.put("error",0);
            map.put("url",IMAGE_BASE_URL+"/image/"+newName);
            return map;
        } catch (Exception e) {
            System.out.println("文件上传失败");
            map.put("error",1);
            map.put("message","文件上传失败");
            return map;
        }
    }
}
