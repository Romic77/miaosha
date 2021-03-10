package com.miaosha.miaoshaproduct.controller;

import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import com.miaosha.miaoshaproduct.domain.entity.Product;
import com.miaosha.miaoshaproduct.service.IProductService;
import com.miaosha.miaoshaproduct.utils.CommonResult;
import com.xkzhangsan.time.converter.DateTimeConverterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
public class ProductController {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

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

        Product product = new Product();
        product.setProductName("台式电脑");
        product.setProductPrice(new BigDecimal("5888.00"));
        product.setContent("支持LOL");
        product.setTotalStocks(500);
        product.setCreateTime(DateTimeConverterUtil.toDate(LocalDateTime.now()));
        return productService.insertProduct(product);
    }

    @RequestMapping("/product/findProductById")
    public CommonResult<ProductDTO> findProductById(@RequestParam("productId") Long productId) {
        try {
            Product product = productService.findProductById(productId);
            ProductDTO productDTO = new ProductDTO();
            BeanUtils.copyProperties(product, productDTO);
            return CommonResult.success(productDTO);
        } catch (Exception e) {
            logger.error("Invoking productService cause ercror:{}", e);

            return CommonResult.failed("product服务异常");
        }
    }

    /**
     * 更新产品库存
     *
     * @return com.miaosha.miaoshaproduct.utils.CommonResult<com.miaosha.miaoshaproduct.domain.dto.ProductDTO>
     * @author chenqi
     * @date 2021/3/5 10:42
     */
    @RequestMapping(value = "/product/updateByPrimaryKeySelective", method = RequestMethod.POST)
    public CommonResult<ProductDTO> updateByPrimaryKeySelective(@RequestBody ProductDTO productDTO) throws Exception {
        try {
            Product product=new Product();
            BeanUtils.copyProperties(productDTO, product);
            productService.updateByPrimaryKeySelective(product);
            return CommonResult.success(null);
        } catch (Exception e) {
            logger.error("Invoking productService cause ercror:{}", e);

            return CommonResult.failed("product服务异常");
        }
    }


}
