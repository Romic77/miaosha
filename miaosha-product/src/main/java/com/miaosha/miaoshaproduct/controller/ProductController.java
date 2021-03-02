package com.miaosha.miaoshaproduct.controller;

import com.miaosha.miaoshaproduct.domain.entity.Product;
import com.miaosha.miaoshaproduct.service.IProductService;
import com.xkzhangsan.time.converter.DateTimeConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
public class ProductController {
    @Autowired
    private IProductService productService;

    /**
     * 1. 主要展示分布式leaf基于DB方式生成product
     * 2. 本地只有一个leaf,到时候部署一个leaf集群环境压测
     *
     * @return java.lang.String
     * @author chenqi
     * @date 2021/3/1 16:39
     */
    @RequestMapping("/insert/product")
    public String insertProduct() throws Exception {

        Product product=new Product();
        product.setProductName("台式电脑");
        product.setProductPrice(new BigDecimal("5888.00"));
        product.setContent("支持LOL");
        product.setTotalStocks(500);
        product.setCreateTime(DateTimeConverterUtil.toDate(LocalDateTime.now()));
        return productService.insertProduct(product);
    }

}
