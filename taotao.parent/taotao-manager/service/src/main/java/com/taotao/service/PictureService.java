package com.taotao.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface PictureService {

    Map uploadPiceure(MultipartFile uploadFile,String path);
}
