package com.miaosha.miaoshaproduct.service.impl;

import com.miaosha.miaoshaproduct.service.IOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements IOrderService {


    public boolean placeOrder(String orderDTO) {
        return false;
    }
}
