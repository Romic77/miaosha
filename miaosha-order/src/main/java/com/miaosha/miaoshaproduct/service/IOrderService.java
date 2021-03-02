package com.miaosha.miaoshaproduct.service;



public interface IOrderService {
    /**
     * 下订单
     * @return boolean
     * @author chenqi
     * @date 2021/3/1 15:41
    */
    public boolean placeOrder(String orderDTO);
}
