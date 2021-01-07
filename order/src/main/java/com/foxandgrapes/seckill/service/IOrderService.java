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
    public Map<String, Object> createOrder(Long seckillGoodsId, Long userId);
}
