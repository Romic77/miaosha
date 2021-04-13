package com.miaosha.miaoshaproduct.service;


import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.utils.CommonResult;

public interface IOrderService {
    /**
     * 用户下订单
     * @return boolean
     * @author chenqi
     * @date 2021/3/1 15:41
    */
    public CommonResult placeOrder(OrderDTO orderDTO);
}
