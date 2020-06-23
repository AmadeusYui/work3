package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.IDUtils;

import java.util.Date;
import java.util.List;

/**
 * 商品管理service
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Override
    public TbItem getItemById(long itemId) {
        //直接用id查询
        //TbItem item = itemMapper.selectByPrimaryKey(itemId);
        //创建一个example对象查询
        TbItemExample example = new TbItemExample();
        //添加查询条件
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemId);
        List<TbItem> items = itemMapper.selectByExample(example);
        if (items!=null && items.size()>0){
            TbItem item = items.get(0);
            return item;
        }
        return null;
    }

    //商品列表查询
    @Override
    public EUDataGridResult getItemList(int page, int rows) {
        //查询商品列表
        TbItemExample example = new TbItemExample();
        //分页处理
        PageHelper.startPage(page,rows);
        List<TbItem> list = itemMapper.selectByExample(example);
        //创建一个返回值对象
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        //取记录的总条数
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    /**
     * 添加一个新的商品
     * @param item
     * @return
     */
    @Override
    public TaotaoResult createItem(TbItem item,String desc,String itemParam) throws Exception {
        //item补全
        //生成商品id
        Long itemId = IDUtils.getItemId();
        item.setId(itemId);
        //商品状态 1-正常 2-下架 3-删除
        item.setStatus((byte) 1);
        //设置添加时间和更新时间
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //商品信息插入到数据库
        itemMapper.insert(item);
        //添加商品描述信息
        TaotaoResult result = insertItemDesc(itemId, desc);
        if (result.getStatus()!=200){
            throw new Exception();
        }
        //添加规格参数
        result = insertItemParamItem(itemId, itemParam);
        if (result.getStatus()!=200){
            throw new Exception();
        }
        return TaotaoResult.ok();
    }

    /**
     * 在同一个事务中调用
     * 用于添加商品描述
     */
    private TaotaoResult insertItemDesc(Long itemId,String desc){
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDescMapper.insert(itemDesc);
        return TaotaoResult.ok();
    }

    /**
     * 添加规格参数
     * @param itemId
     * @param itemParam
     * @return
     */
    private TaotaoResult insertItemParamItem(Long itemId,String itemParam){
        //创建pojo
        TbItemParamItem itemParamItem = new TbItemParamItem();
        //添加数据
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParam);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        //向表中添加数据
        itemParamItemMapper.insert(itemParamItem);
        return TaotaoResult.ok();
    }
}
