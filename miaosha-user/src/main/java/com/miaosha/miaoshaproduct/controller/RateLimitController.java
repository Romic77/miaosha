package com.miaosha.miaoshaproduct.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    @GetMapping("/byResource")
    @SentinelResource(value = "byResource",blockHandler = "handleException")
    public String byResource(){
        return "ok";
    }
    /**
     * 这是兜底的方法
     * @param e
     * @return
     */
    public String handleException(BlockException e){
        return "服务不可用";
    }
}