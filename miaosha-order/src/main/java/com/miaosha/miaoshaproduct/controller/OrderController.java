package com.miaosha.miaoshaproduct.controller;


import com.miaosha.miaoshaproduct.utils.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @RequestMapping("/order/placeOrder")
    public CommonResult placeOrder(String orderDto){
        //1. 根据productId查询Redis库存
        //2. 保证原子性 对Redis进行操作
        return CommonResult.success(null);
    }
}
