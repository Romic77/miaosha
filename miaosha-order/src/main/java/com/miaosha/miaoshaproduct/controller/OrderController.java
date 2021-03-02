package com.miaosha.miaoshaproduct.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @RequestMapping("/order/placeOrder")
    public String placeOrder(String orderDto){
        //1. 根据productId查询Redis库存
        //2. 保证原子性 对Redis进行操作
        return "";
    }
}
