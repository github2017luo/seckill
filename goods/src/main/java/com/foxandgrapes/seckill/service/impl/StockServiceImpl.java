package com.foxandgrapes.seckill.service.impl;

import com.foxandgrapes.seckill.mapper.SeckillGoodsMapper;
import com.foxandgrapes.seckill.pojo.SeckillGoods;
import com.foxandgrapes.seckill.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StockServiceImpl implements IStockService {

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Override
    public Map<String, Object> updateStock(Long seckillGoodsId, Integer inputStock, Integer outputStock) {

        Map<String, Object> resultMap = new HashMap<>();

        if (seckillGoodsId == null) {
            resultMap.put("result", false);
            resultMap.put("msg", "秒杀商品ID不能为空！");
            return resultMap;
        }
        if (inputStock == 0 && outputStock == 0) {
            resultMap.put("result", false);
            resultMap.put("msg", "入库数量和出库数量不能同时为0！");
            return resultMap;
        }
        SeckillGoods seckillGoods = seckillGoodsMapper.selectById(seckillGoodsId);
        if (seckillGoods == null) {
            resultMap.put("result", false);
            resultMap.put("msg", "该秒杀商品不存在！");
            return resultMap;
        }
        int newStock = seckillGoods.getSeckillStock() + inputStock - outputStock;
        if (newStock < 0) {
            resultMap.put("result", false);
            resultMap.put("msg", "新的库存不能小于0！");
            return resultMap;
        }

        seckillGoods.setSeckillStock(newStock);
        int res = seckillGoodsMapper.updateById(seckillGoods);
        if (res != 1) {
            resultMap.put("result", false);
            resultMap.put("msg", "更新库存失败！");
            return resultMap;
        }

        resultMap.put("result", true);
        resultMap.put("msg", "更新库存成功！");
        return resultMap;
    }
}
