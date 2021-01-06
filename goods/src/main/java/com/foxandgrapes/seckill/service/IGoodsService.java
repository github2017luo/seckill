package com.foxandgrapes.seckill.service;

import com.foxandgrapes.seckill.pojo.Goods;
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
public interface IGoodsService extends IService<Goods> {

    Map<String, Object> getGoodsList();

    Map<String, Object> getGoods(Long id);
}
