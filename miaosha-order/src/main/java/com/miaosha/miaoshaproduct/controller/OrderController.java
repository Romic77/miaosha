package com.miaosha.miaoshaproduct.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @RequestMapping("/order/placeOrder")
    public String placeOrder(String orderDto){

        return "";
    }
}
