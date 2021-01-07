package com.foxandgrapes.seckill.service;

import java.util.Map;

public interface IStockService {

    /**
     * 更新商品为ID的库存数量
     * @param goodsId
     * @param input
     * @param output
     * @return
     */
    Map<String, Object> updateStock(Long goodsId, Integer input, Integer output);
}
