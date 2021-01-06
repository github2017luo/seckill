package com.foxandgrapes.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxandgrapes.seckill.mapper.SeckillGoodsMapper;
import com.foxandgrapes.seckill.pojo.SeckillGoods;
import com.foxandgrapes.seckill.service.ISeckillGoodsService;
import com.foxandgrapes.seckill.service.ITimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ITimeService timeService;

    @Override
    public Map<String, Object> insertSeckillGoods(SeckillGoods seckillGoods) {
        Map<String, Object> map = new HashMap<>();
        if (seckillGoods == null || seckillGoods.getGoodsId() == null ||
                seckillGoods.getStartDate() == null || seckillGoods.getEndDate() == null) {
            map.put("result", "false");
            map.put("msg", "秒杀商品添加失败！");
            return map;
        }
        if (seckillGoods.getEndDate().before(seckillGoods.getStartDate())) {
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

        long diff = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date now = simpleDateFormat.parse(timeService.getTime());
            diff = (seckillGoods.getEndDate().getTime() - now.getTime()) / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        redisTemplate.opsForValue().set("SECKILL_GOODS_" + seckillGoods.getGoodsId(), seckillGoods, diff, TimeUnit.SECONDS);


        map.put("result", "true");
        map.put("msg", "秒杀商品添加成功!");
        map.put("insertSeckillGoods", seckillGoods);

        return map;
    }
}