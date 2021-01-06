package com.foxandgrapes.seckill.service;

import com.foxandgrapes.seckill.pojo.SeckillGoods;
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
public interface ISeckillGoodsService extends IService<SeckillGoods> {

    Map<String, Object> insertSeckillGoods(SeckillGoods seckillGoods);
}
