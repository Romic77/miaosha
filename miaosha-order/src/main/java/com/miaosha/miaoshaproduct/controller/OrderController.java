package com.miaosha.miaoshaproduct.controller;


import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import com.miaosha.miaoshaproduct.redisson.DistributedLocker;
import com.miaosha.miaoshaproduct.service.IOrderService;
import com.miaosha.miaoshaproduct.service.LeafFeignService;
import com.miaosha.miaoshaproduct.service.ProductFeignService;
import com.miaosha.miaoshaproduct.utils.CommonResult;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenqi
 * @date 2021/3/9 09:30
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private LeafFeignService leafFeignService;

    @Autowired
    private DistributedLocker distributedLocker;

    /**
     * 使用 rocketmq 进行订单库存 发布订阅的时候
     * 下游正在消费最后一条的时候，上游刚好查询库存还有 所以会超卖(核心问题就是 生产速度 > 消费速度)
     * 1. 使用seata AT模式 强一致性(需要测试性能)
     * 2. 查询产品库存，然后把用户消费的库存存入redis。存完之后 通知库存从redis拉取(最终一致性)
     *
     * @param orderDTO
     * @return
     */
    @RequestMapping(value = "/placeOrder", method = RequestMethod.POST,
            produces = "application/json; charset=UTF-8", consumes = "application/json;charset=UTF-8")
    public CommonResult placeOrder(@RequestBody OrderDTO orderDTO) {
        RLock lock = distributedLocker.lock("placeOrder");
        lock.lock();
        try {
            CommonResult<ProductDTO> productDTOCommonResult = productFeignService.findProductById(orderDTO.getProductId());
            if (productDTOCommonResult.getCode() != 200) {
                return productDTOCommonResult;
            }

            ProductDTO productDTO = productDTOCommonResult.getData();
            if (productDTO.getTotalStocks() > 0) {
                logger.info("下单成功，当前库存为:{}", productDTO.getTotalStocks());
                orderService.placeOrder(orderDTO);
            } else {
                return CommonResult.failed("下单失败，库存不足");
            }
            return CommonResult.success(null);
        } catch (Exception e) {
            logger.error("订单服务异常: ", e);
            return CommonResult.failed("placeOrder业务异常");
        } finally {
            lock.unlock();
        }
    }
}
