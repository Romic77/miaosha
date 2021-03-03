package com.miaosha.miaoshaproduct.service;


import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import com.miaosha.miaoshaproduct.service.impl.ProductServiceFallBack;
import com.miaosha.miaoshaproduct.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: chenq
 * @Description:
 * @Date: Created in 2021-03-02 09:52
 */
@FeignClient(value="miaosha-product",fallback = ProductServiceFallBack.class)
public interface ProductFeignService {

    @RequestMapping("/product/findProductById")
    public CommonResult<ProductDTO> findProductById(@RequestParam("productId")Long productId);

}
