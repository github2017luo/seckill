package com.foxandgrapes.seckill.service;

import com.foxandgrapes.seckill.vo.RespBean;

public interface IStockService {

    /**
     * 更新商品为ID的库存数量
     * @param seckillGoodsId
     * @param input
     * @param output
     * @return
     */
    RespBean updateStock(Long seckillGoodsId, Integer input, Integer output);

    /**
     * 库存减一
     * @param seckillGoodsId
     * @return
     */
    RespBean decrementStock(Long seckillGoodsId);
}
