package com.foxandgrapes.seckill.service.impl;

import com.foxandgrapes.seckill.pojo.SeckillGoods;
import com.foxandgrapes.seckill.mapper.SeckillGoodsMapper;
import com.foxandgrapes.seckill.service.ISeckillGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tsk
 * @since 2021-01-06
 */
@Service
public class SeckillGoodsServiceImpl extends ServiceImpl<SeckillGoodsMapper, SeckillGoods> implements ISeckillGoodsService {

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Override
    public Map<String, Object> insertSeckillGoods(SeckillGoods seckillGoods) {
        Map<String, Object> map = new HashMap<>();
        if (seckillGoods == null) {
            map.put("result", "false");
            map.put("msg", "秒杀商品添加失败！");
            return map;
        }
        int ret = seckillGoodsMapper.insert(seckillGoods);
        if (ret != 1) {
            map.put("result", "false");
            map.put("msg", "秒杀商品添加失败！");
            return map;
        }
        map.put("result", "true");
        map.put("msg", "秒杀商品添加成功!");
        map.put("insertSeckillGoods", seckillGoods);
        return map;
    }
}
