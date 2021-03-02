package com.miaosha.miaoshaproduct.service;

import com.miaosha.miaoshaproduct.domain.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: chenq
 * @Description:
 * @Date: Created in 2021-03-02 09:52
 */
@FeignClient("miaosha-product")
public interface ProductFeignService {

    @RequestMapping("/product/findProductById")
    public Product findProductById(Long productId);

}
