package com.foxandgrapes.seckill.service.impl;

import com.foxandgrapes.seckill.pojo.Goods;
import com.foxandgrapes.seckill.pojo.Order;
import com.foxandgrapes.seckill.mapper.OrderMapper;
import com.foxandgrapes.seckill.pojo.SeckillGoods;
import com.foxandgrapes.seckill.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxandgrapes.seckill.service.ITimeController;
import org.springframework.amqp.core.AmqpTemplate;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ITimeController timeController;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public Map<String, Object> createOrder(Long seckillGoodsId, Long userId) {
        Map<String, Object> resultMap = new HashMap<>();

        if (seckillGoodsId == null || userId == null) {
            resultMap.put("result", false);
            resultMap.put("msg", "商品ID或用户ID不能为空！");
            return resultMap;
        }

        // 从redis中取出秒杀商品信息
        SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.opsForValue().get("SECKILL_GOODS_" + seckillGoodsId);

        if (seckillGoods == null) {
            resultMap.put("result", false);
            resultMap.put("msg", "活动已结束！");
            return resultMap;
        }

        // 获取当前时间，判断是否为活动时间
        Date now = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            now = simpleDateFormat.parse(timeController.getTime());
            // 没到秒杀时间或已过期
            if (seckillGoods.getStartDate().getTime() > now.getTime() ||
                    now.getTime() > seckillGoods.getEndDate().getTime()) {
                resultMap.put("result", false);
                resultMap.put("msg", "活动还没开始或已结束！");
                return resultMap;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            resultMap.put("result", false);
            resultMap.put("msg", "时间转换出现错误！");
            return resultMap;
        }

        // 有秒杀商品，以及到活动时间了，开始秒杀！
        // 初始化redis中该商品的库存，只初始化一次
        redisTemplate.opsForValue().setIfAbsent("SECKILL_GOODS_COUNT_" + seckillGoods, seckillGoods.getStockCount());

        if (redisTemplate.opsForValue().decrement("SECKILL_GOODS_COUNT_" + seckillGoods) < 0) {
            // 归还库存
            redisTemplate.delete("SECKILL_GOODS_COUNT_" + seckillGoods);
            resultMap.put("result", false);
            resultMap.put("msg", "商品已售完！");
            return resultMap;
        } else {
            // 加分布式锁,30秒自动释放
            if (redisTemplate.opsForValue().setIfAbsent("SECKILL_GOODS_KEY_" + seckillGoods, userId, 30,
                    TimeUnit.SECONDS)) {
                try {
                    // 抢到商品了！生成秒杀订单号！
                    long orderId = System.currentTimeMillis();
                    // 从商品信息从redis中取出来
                    Goods goods = (Goods) redisTemplate.opsForValue().get("GOODS_" + seckillGoods.getGoodsId());
                    // 将订单信息写入到消息队列中待处理
                    Order order = new Order();
                    order.setId(orderId);
                    order.setUserId(userId);
                    order.setGoodsId(goods.getId());
                    order.setGoodsName(goods.getGoodsName());
                    order.setGoodsCount(1);
                    order.setGoodsPrice(seckillGoods.getSeckillPrice());
                    order.setStatus(0);
                    order.setCreateDate(now);

                    redisTemplate.opsForValue().set("ORDER_" + orderId, order);
                    amqpTemplate.convertAndSend("order_queue", order);
                } catch (Exception e) {
                    // 异常的时候自动释放自己的锁
                    if (redisTemplate.opsForValue().get("SECKILL_GOODS_KEY_" + seckillGoods).toString().equals(userId.toString())) {
                        redisTemplate.delete("SECKILL_GOODS_KEY_" + seckillGoods);
                    }
                    // 归还库存
                    redisTemplate.opsForValue().increment("SECKILL_GOODS_COUNT_" + seckillGoods);
                    resultMap.put("result", false);
                    resultMap.put("msg", "秒杀期间出现了某种错误！");
                    return resultMap;
                }
            } else {
                resultMap.put("result", false);
                resultMap.put("msg", "就差一点就抢到了！");
                return resultMap;
            }
        }

        // 已抢到商品，释放自己的锁
        if (redisTemplate.opsForValue().get("SECKILL_GOODS_KEY_" + seckillGoods).toString().equals(userId.toString())) {
            redisTemplate.delete("SECKILL_GOODS_KEY_" + seckillGoods);
        }
        resultMap.put("result", true);
        resultMap.put("msg", "秒杀成功！");
        return resultMap;
    }
}
