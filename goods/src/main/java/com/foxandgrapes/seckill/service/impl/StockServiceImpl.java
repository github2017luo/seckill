package com.foxandgrapes.seckill.service.impl;

import com.foxandgrapes.seckill.mapper.SeckillGoodsMapper;
import com.foxandgrapes.seckill.pojo.SeckillGoods;
import com.foxandgrapes.seckill.service.IStockService;
import com.foxandgrapes.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements IStockService {

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Override
    public RespBean updateStock(Long seckillGoodsId, Integer inputStock, Integer outputStock) {

        if (seckillGoodsId == null) {
            return RespBean.error("秒杀商品ID不能为空！");
        }
        if (inputStock == 0 && outputStock == 0) {
            return RespBean.error("入库数量和出库数量不能同时为0！");
        }
        SeckillGoods seckillGoods = seckillGoodsMapper.selectById(seckillGoodsId);
        if (seckillGoods == null) {
            return RespBean.error("该秒杀商品不存在！");
        }
        int newStock = seckillGoods.getSeckillStock() + inputStock - outputStock;
        if (newStock < 0) {
            return RespBean.error("新的库存不能小于0！");
        }

        seckillGoods.setSeckillStock(newStock);
        int res = seckillGoodsMapper.updateById(seckillGoods);
        if (res != 1) {
            return RespBean.error("更新库存失败！");
        }

        return RespBean.success( "更新库存成功！",null);
    }
}
