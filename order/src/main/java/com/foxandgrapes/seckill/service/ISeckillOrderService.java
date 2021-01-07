package com.foxandgrapes.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.foxandgrapes.seckill.pojo.SeckillOrder;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tsk
 * @since 2021-01-06
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    public Map<String, Object> createSeckillOrder(Long seckillGoodsId, Long userId);
}
