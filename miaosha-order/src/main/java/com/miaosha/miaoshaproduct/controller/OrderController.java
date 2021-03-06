package com.miaosha.miaoshaproduct.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import com.miaosha.miaoshaproduct.service.IOrderService;
import com.miaosha.miaoshaproduct.service.ProductFeignService;
import com.miaosha.miaoshaproduct.utils.CommonResult;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ProductFeignService productFeignService;

    @RequestMapping(value = "/order/placeOrder", method = RequestMethod.POST,
            produces = "application/json; charset=UTF-8", consumes = "application/json;charset=UTF-8")
    public CommonResult placeOrder(@RequestBody OrderDTO orderDTO) {
        RLock redissonLock = redissonClient.getLock("lock");
        try {
            redissonLock.lock();
            CommonResult<ProductDTO> productDTOCommonResult = productFeignService.findProductById(orderDTO.getProductId());
            if (productDTOCommonResult.getCode() != 200) {
                return productDTOCommonResult;
            }

            ProductDTO productDTO = productDTOCommonResult.getData();
            if (productDTO.getTotalStocks() > 0) {
                logger.info("下单成功，当前库存为:{}", productDTO.getTotalStocks());
                orderService.placeOrder(orderDTO, productDTO);
            } else {
                return CommonResult.failed("下单失败，库存不足");
            }

            return CommonResult.success(null);
        } catch (Exception e) {
            logger.error("订单服务异常: ", e);
            return CommonResult.failed("placeOrder业务异常");
        } finally {
            redissonLock.unlock();
        }
    }
}
