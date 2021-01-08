package com.foxandgrapes.seckill.service.impl;

import com.foxandgrapes.seckill.mapper.GoodsMapper;
import com.foxandgrapes.seckill.pojo.Goods;
import com.foxandgrapes.seckill.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StockServiceImpl implements IStockService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Map<String, Object> updateStock(Long goodsId, Integer inputStock, Integer outputStock) {

        Map<String, Object> resultMap = new HashMap<>();

        if (goodsId == null) {
            resultMap.put("result", false);
            resultMap.put("msg", "商品ID不能为空！");
            return resultMap;
        }
        if (inputStock == 0 && outputStock == 0) {
            resultMap.put("result", false);
            resultMap.put("msg", "入库数量和出库数量不能同时为0！");
            return resultMap;
        }
        Goods goods = goodsMapper.selectById(goodsId);
        int newStock = goods.getGoodsStock() + inputStock - outputStock;
        if (newStock < 0) {
            resultMap.put("result", false);
            resultMap.put("msg", "新的库存不能小于0！");
            return resultMap;
        }

        goods.setGoodsStock(newStock);
        goodsMapper.updateById(goods);

        resultMap.put("result", true);
        resultMap.put("msg", "更新库存成功！");
        return resultMap;
    }
}
