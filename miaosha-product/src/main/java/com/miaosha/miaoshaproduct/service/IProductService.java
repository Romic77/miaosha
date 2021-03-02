package com.miaosha.miaoshaproduct.service;

import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import com.miaosha.miaoshaproduct.domain.entity.Product;

public interface IProductService {
    String insertProduct(ProductDTO product) throws Exception;

    Product findProductById(Long productId);
}
