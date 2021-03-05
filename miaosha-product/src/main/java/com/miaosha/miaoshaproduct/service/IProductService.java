package com.miaosha.miaoshaproduct.service;

import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import com.miaosha.miaoshaproduct.domain.entity.Product;
import com.miaosha.miaoshaproduct.utils.CommonResult;

public interface IProductService {
    String insertProduct(ProductDTO product) throws Exception;

    Product findProductById(Long productId);

    CommonResult<ProductDTO> updateByPrimaryKeySelective(ProductDTO productDTO);
}
