package com.taotao.rest.service.impl;


import com.taotao.common.pojo.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类服务
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Autowired
    JedisClient jedisClient;

    @Value("${INDEX_CAT_REDIS_KEY}")
    String INDEX_CAT_REDIS_KEY;

    @Override
    public CatResult getItemCatList() {
        CatResult catResult = new CatResult();
        //查询分类列表
        catResult.setData(getCatList(0));
        return catResult;
    }

    /**
     * 查询分类列表
     * @param parentId
     * @return
     */
    private List<?> getCatList(long parentId){
        //查找缓存内容
        try {
            String result = jedisClient.hget(INDEX_CAT_REDIS_KEY, parentId+"");
            if(!StringUtils.isBlank(result)){
                List<CatNode> resultList = JsonUtils.jsonToList(result, CatNode.class);
                return resultList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItemCatExample examle = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = examle.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> list = itemCatMapper.selectByExample(examle);
        //返回值list
        int count = 0;
        List resultList = new ArrayList<>();
        for (TbItemCat tbItemCat : list) {
            //判断是否为父节点
            if (tbItemCat.getIsParent()){
                CatNode catNode = new CatNode();
                if (parentId==0){
                    catNode.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
                }else{
                    catNode.setName(tbItemCat.getName());
                }
                catNode.setUrl("/products/"+tbItemCat.getId()+".html");
                catNode.setItem(getCatList(tbItemCat.getId()));
                resultList.add(catNode);
                count++;
                //防止超长
                if ( parentId == 0 && count >= 14 ){
                    break;
                }
                //叶子节点
            }else{
                resultList.add("/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName());
            }
        }
        //存储进redis缓存
        try {
            String cacheString = JsonUtils.objectToJson(resultList);
            jedisClient.hset(INDEX_CAT_REDIS_KEY,parentId+"",cacheString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
