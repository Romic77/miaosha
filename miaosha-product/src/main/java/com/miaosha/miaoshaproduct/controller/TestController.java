package com.miaosha.miaoshaproduct.controller;

import com.miaosha.miaoshaproduct.service.LeafFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private LeafFeignService feignLeafService;

    @RequestMapping(value = "/api/segment/get/{key}")
    public String getSegmentId(@PathVariable("key") String key) {
        return feignLeafService.getSegmentId(key);
    }

}
