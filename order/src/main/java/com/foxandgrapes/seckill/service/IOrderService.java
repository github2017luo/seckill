package com.foxandgrapes.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.foxandgrapes.seckill.pojo.Order;
import com.foxandgrapes.seckill.vo.RespBean;

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
    RespBean createOrder(Long seckillGoodsId, Long userId);

    /**
     * 将订单写入到数据库
     * @param order
     * @return
     */
    RespBean insertOrder(Order order);

    /**
     * 获取订单
     * @param orderId
     * @return
     */
    RespBean getOrder(Long orderId);

    RespBean payOrder(Long orderId, Long seckillGoodsId);
}
