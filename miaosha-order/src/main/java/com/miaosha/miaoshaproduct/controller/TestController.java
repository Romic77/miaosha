package com.miaosha.miaoshaproduct.controller;

import com.alibaba.fastjson.JSONObject;
import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import com.miaosha.miaoshaproduct.rocketmq.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "test")
public class TestController {
    @Autowired
    SenderService senderService;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String send(String msg) {
        senderService.send("test-topic1",msg);
        return "字符串消息发送成功!";
    }

    @RequestMapping(value = "/sendWithTags", method = RequestMethod.GET)
    public String sendWithTags(String msg) {
        senderService.sendWithTags("test-topic1",msg, "tagStr");
        return "带tag字符串消息发送成功!";
    }

    @RequestMapping(value = "/sendObject", method = RequestMethod.GET)
    public String sendObject() {
        ProductDTO productDTO=new ProductDTO();
        productDTO.setProductId(123l);
        senderService.sendObject("test-topic2",productDTO, "tagObj");
        return "Object对象消息发送成功!";
    }

    @RequestMapping(value = "/sendMessageInTransaction", method = RequestMethod.GET)
    public String sendMessageInTransaction() {
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setUserId("1l");
        orderDTO.setProductId(2001l);
        orderDTO.setProductName("电脑");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("orderDTO",orderDTO);
        jsonObject.put("productDTO",null);
        senderService.sendMessageInTransaction("miaosha_order_placeorder",jsonObject);
        return "Object对象消息发送成功!";
    }

}