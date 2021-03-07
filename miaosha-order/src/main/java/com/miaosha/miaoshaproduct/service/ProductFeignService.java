package com.miaosha.miaoshaproduct.service;


import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import com.miaosha.miaoshaproduct.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: chenq
 * @Description:
 * @Date: Created in 2021-03-02 09:52
 */
@FeignClient(value = "miaosha-product")
public interface ProductFeignService {

    @RequestMapping(value = "/product/updateByPrimaryKeySelective", method = RequestMethod.POST)
    public CommonResult<ProductDTO> updateByPrimaryKeySelective(@RequestBody ProductDTO productDTO);

    @RequestMapping("/product/findProductById")
    public CommonResult<ProductDTO> findProductById(@RequestParam("productId") Long productId);

}
