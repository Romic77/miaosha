package com.miaosha.miaoshaproduct.service.impl;

import com.miaosha.miaoshaproduct.domain.dao.ProductMapper;
import com.miaosha.miaoshaproduct.domain.entity.Product;
import com.miaosha.miaoshaproduct.service.IProductService;
import com.miaosha.miaoshaproduct.service.LeafFeignService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    @Autowired
    private LeafFeignService leafFeignService;

    @Autowired
    private ProductMapper productMapper;

    public String insertProduct(Product product) throws Exception {
        String id=leafFeignService.getSegmentId("leaf-segment-product");
        if(StringUtils.isBlank(id)){
            throw new Exception("leafFeignService获取id为空,检查leaf服务器");
        }
        product.setProductId(Long.valueOf(id));
        productMapper.insert(product);
        return product.toString();
    }
}
