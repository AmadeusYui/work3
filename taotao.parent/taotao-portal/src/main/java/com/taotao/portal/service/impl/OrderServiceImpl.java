package com.taotao.portal.service.impl;

import com.taotao.common.pojo.JsonUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import utils.HttpClientUtil;

/**
 * 订单处理service
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Value("${ORDER_BASE_URL}")
    private String ORDER_BASE_URL;

    @Value("${ORDER_CREATE_URL}")
    private String ORDER_CREATE_URL;

    @Override
    public String createOrder(Order order) {
        //调用taotaoorder的服务提交订单
        String json = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, JsonUtils.objectToJson(order));
        //把json转换成taotaoresult
        TaotaoResult taotaoResult = TaotaoResult.format(json);
        if (taotaoResult.getStatus()==200){
            Object orderId = taotaoResult.getData();
            return orderId.toString();
        }
        return "";
    }
}
