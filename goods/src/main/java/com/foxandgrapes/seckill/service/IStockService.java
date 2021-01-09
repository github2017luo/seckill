package com.foxandgrapes.seckill.service;

import com.foxandgrapes.seckill.vo.RespBean;

public interface IStockService {

    /**
     * 更新商品为ID的库存数量
     * @param goodsId
     * @param input
     * @param output
     * @return
     */
    RespBean updateStock(Long goodsId, Integer input, Integer output);
}
