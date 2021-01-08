package com.foxandgrapes.seckill.service;

import com.foxandgrapes.seckill.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tsk
 * @since 2021-01-06
 */
public interface IOrderService extends IService<Order> {

    /**
     * 在内存中创建订单
     * @param seckillGoodsId
     * @param userId
     * @return
     */
    Map<String, Object> createOrder(Long seckillGoodsId, Long userId);

    /**
     * 将订单写入到数据库
     * @param order
     * @return
     */
    Map<String, Object> insertOrder(Order order);
}
