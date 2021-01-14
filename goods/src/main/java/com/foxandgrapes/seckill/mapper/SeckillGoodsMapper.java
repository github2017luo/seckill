package com.foxandgrapes.seckill.mapper;

import com.foxandgrapes.seckill.pojo.SeckillGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tsk
 * @since 2021-01-06
 */
public interface SeckillGoodsMapper extends BaseMapper<SeckillGoods> {
    Integer decrementStock(Long seckillGoodsId);
}
