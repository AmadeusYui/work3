package com.taotao.portal.service.impl;

import com.taotao.common.pojo.JsonUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import utils.HttpClientUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调用服务层查询内容列表
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${REST_INDEX_AD_URL}")
    private String REST_INDEX_AD_URL;

    @Override
    public String getContentList() {
        //调用服务层的服务
        String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
        //把字符串转换成taotaoresult
        try {
            TaotaoResult taotaoResult = TaotaoResult.formatToList(result, TbContent.class);
            //取出内容
            List<TbContent> list = (List<TbContent>)taotaoResult.getData();
            //创建一个jsp页面要求的pojo列表
            List<Map> resultList = new ArrayList<>();
            for (TbContent tbContent : list) {
                Map map = new HashMap();
                map.put("src","http://localhost:8080"+tbContent.getPic());
                map.put("height",240);
                map.put("width",670);
                map.put("srcB","http://localhost:8080"+tbContent.getPic2());
                map.put("widthB",550);
                map.put("heightB",240);
                map.put("href",tbContent.getUrl());
                map.put("alt",tbContent.getSubTitle());
                resultList.add(map);
            }
            return JsonUtils.objectToJson(resultList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
