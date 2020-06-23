package com.taotao.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 商品的规格参数模板
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    private TbItemParamMapper itemParamMapper;

    /**
     * 判断数据库中是否已经有该类目的模板,并且把模板返回到页面
     * @param cid
     * @return
     */
    @Override
    public TaotaoResult getItemParamByCid(long cid) {
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
        //判断是否查询到结果
        if (list!=null && list.size()>0){
            return TaotaoResult.ok(list.get(0));
        }
        return TaotaoResult.ok();
    }

    /**
     * 实现新增一条模板
     * @param itemParam
     * @return
     */
    @Override
    public TaotaoResult insertItemParam(TbItemParam itemParam) {
        //补全pojo
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        //插入到规格参数模板表
        itemParamMapper.insert(itemParam);
        return TaotaoResult.ok();
    }
}
