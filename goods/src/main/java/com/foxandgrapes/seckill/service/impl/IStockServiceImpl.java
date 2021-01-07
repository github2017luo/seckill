package com.foxandgrapes.seckill.service.impl;

import com.foxandgrapes.seckill.mapper.GoodsMapper;
import com.foxandgrapes.seckill.pojo.Goods;
import com.foxandgrapes.seckill.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IStockServiceImpl implements IStockService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Map<String, Object> updateStock(Long goodsId, Integer inputStock, Integer outputStock) {

        Map<String, Object> map = new HashMap<>();

        if (goodsId == null) {
            map.put("result", false);
            map.put("msg", "商品ID不能为空！");
            return map;
        }
        if (inputStock == 0 && outputStock == 0) {
            map.put("result", false);
            map.put("msg", "入库数量和出库数量不能同时为0！");
            return map;
        }
        Goods goods = goodsMapper.selectById(goodsId);
        int newStock = goods.getGoodsStock() + inputStock - outputStock;
        if (newStock < 0) {
            map.put("result", false);
            map.put("msg", "新的库存不能小于0！");
            return map;
        }

        goods.setGoodsStock(newStock);
        goodsMapper.updateById(goods);

        map.put("result", true);
        map.put("msg", "更新库存成功！");
        return map;
    }
}
