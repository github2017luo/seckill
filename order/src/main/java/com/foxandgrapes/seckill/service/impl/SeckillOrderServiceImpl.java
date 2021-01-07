package com.foxandgrapes.seckill.service.impl;

import com.foxandgrapes.seckill.pojo.SeckillOrder;
import com.foxandgrapes.seckill.mapper.SeckillOrderMapper;
import com.foxandgrapes.seckill.service.ISeckillOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> createSeckillOrder(Long seckillGoodsId, Long userId) {
        Map<String, Object> resultMap = new HashMap<>();

        if (seckillGoodsId == null || userId == null) {
            resultMap.put("result", false);
            resultMap.put("msg", "商品ID或用户ID不能为空！");
            return resultMap;
        }

        redisTemplate.opsForValue().get("SECKILL_GOODS_" + seckillGoodsId);


        return resultMap;
    }
}
