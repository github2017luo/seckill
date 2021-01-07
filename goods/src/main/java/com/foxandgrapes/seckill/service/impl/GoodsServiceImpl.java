package com.foxandgrapes.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxandgrapes.seckill.mapper.GoodsMapper;
import com.foxandgrapes.seckill.pojo.Goods;
import com.foxandgrapes.seckill.pojo.SeckillGoods;
import com.foxandgrapes.seckill.service.IGoodsService;
import com.foxandgrapes.seckill.service.ITimeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tsk
 * @since 2021-01-06
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ITimeController timeController;

    @Override
    public Map<String, Object> getGoodsList() {
        Map<String, Object> map = new HashMap<>();
        List<Goods> goodsList = goodsMapper.selectList(null);
        if (goodsList == null || goodsList.size() == 0) {
            map.put("result", "false");
            map.put("msg", "商品信息获取失败！");
            return map;
        }

        // 添加秒杀信息
        for (Goods goods : goodsList) {
            getSeckillDetail(goods);
        }

        map.put("result", "true");
        map.put("goodsList", goodsList);
        return map;
    }

    @Override
    public Map<String, Object> getGoods(Long id) {
        Map<String, Object> map = new HashMap<>();
        Goods goods = goodsMapper.selectById(id);
        if (goods == null) {
            map.put("result", "false");
            map.put("msg", "查询无果！");
            return map;
        }

        // 添加秒杀信息
        getSeckillDetail(goods);

        map.put("result", "true");
        map.put("goods", goods);
        return map;
    }

    private void getSeckillDetail(Goods goods) {
        if (goods == null) return;

        // 取redis中的秒杀商品信息
        SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.opsForValue().get("SECKILL_GOODS_" + goods.getId());

        if (seckillGoods != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            try {
                Date now = simpleDateFormat.parse(timeController.getTime());

                // 在秒杀结束时间前
                if (now.getTime() <= seckillGoods.getEndDate().getTime()) {
                    // 添加秒杀信息
                    goods.setSeckillGoods(seckillGoods);
                    goods.setNowTime(now);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
