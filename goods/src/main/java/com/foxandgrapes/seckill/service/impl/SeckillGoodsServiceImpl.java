package com.foxandgrapes.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxandgrapes.seckill.mapper.GoodsMapper;
import com.foxandgrapes.seckill.mapper.SeckillGoodsMapper;
import com.foxandgrapes.seckill.pojo.Goods;
import com.foxandgrapes.seckill.pojo.SeckillGoods;
import com.foxandgrapes.seckill.service.ISeckillGoodsService;
import com.foxandgrapes.seckill.service.ITimeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    private ITimeController timeController;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Map<String, Object> insertSeckillGoods(SeckillGoods seckillGoods) {

        Map<String, Object> map = new HashMap<>();
        if (seckillGoods == null || seckillGoods.getGoodsId() == null ||
                seckillGoods.getStartDate() == null || seckillGoods.getEndDate() == null) {
            map.put("result", false);
            map.put("msg", "秒杀商品信息不能为空！");
            return map;
        }
        if (seckillGoods.getEndDate().before(seckillGoods.getStartDate())) {
            map.put("result", false);
            map.put("msg", "秒杀商品结束时间不能小于开始时间！");
            return map;
        }

        int ret = seckillGoodsMapper.insert(seckillGoods);
        if (ret != 1) {
            map.put("result", false);
            map.put("msg", "秒杀商品添加失败！");
            return map;
        }

        long diff = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Date now = simpleDateFormat.parse(timeController.getTime());
            diff = (seckillGoods.getEndDate().getTime() - now.getTime()) / 1000;
            if (diff <= 0) {
                map.put("result", false);
                map.put("msg", "秒杀商品结束时间不能小于当前时间！");
                return map;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 添加秒杀商品缓存
        redisTemplate.opsForValue().set("SECKILL_GOODS_" + seckillGoods.getGoodsId(), seckillGoods, diff, TimeUnit.SECONDS);

        // 添加商品缓存
        Goods goods = goodsMapper.selectById(seckillGoods.getGoodsId());
        redisTemplate.opsForValue().set("GOODS_" + goods.getId(), goods, diff, TimeUnit.SECONDS);

        map.put("result", true);
        map.put("msg", "秒杀商品添加成功并缓存成功!");

        return map;
    }
}