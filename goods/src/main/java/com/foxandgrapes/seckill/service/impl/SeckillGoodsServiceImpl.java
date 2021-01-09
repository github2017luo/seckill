package com.foxandgrapes.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxandgrapes.seckill.mapper.GoodsMapper;
import com.foxandgrapes.seckill.mapper.SeckillGoodsMapper;
import com.foxandgrapes.seckill.pojo.Goods;
import com.foxandgrapes.seckill.pojo.SeckillGoods;
import com.foxandgrapes.seckill.service.ISeckillGoodsService;
import com.foxandgrapes.seckill.service.ITimeController;
import com.foxandgrapes.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private StringRedisTemplate stringRedisTemplate;    // 专门来处理存放数值的
    @Autowired
    private ITimeController timeController;
    @Autowired
    private GoodsMapper goodsMapper;

    // 开启事务，因为有两条sql语句
    @Transactional
    @Override
    public RespBean insertSeckillGoods(SeckillGoods seckillGoods) {

        if (seckillGoods == null || seckillGoods.getGoodsId() == null ||
                seckillGoods.getStartDate() == null || seckillGoods.getEndDate() == null ||
                seckillGoods.getSeckillStock() == null || seckillGoods.getSeckillPrice() == null) {
            return RespBean.error("秒杀商品信息不能有空！");
        }

        if (seckillGoods.getEndDate().before(seckillGoods.getStartDate())) {
            return RespBean.error("秒杀商品结束时间不能小于开始时间！");
        }

        Goods goods = goodsMapper.selectById(seckillGoods.getGoodsId());
        if (goods == null) {
            return RespBean.error("秒杀商品不存在！");
        }

        // 生成秒杀商品的id
        seckillGoods.setId(System.currentTimeMillis());
        int res = seckillGoodsMapper.insert(seckillGoods);
        if (res != 1) {
            return RespBean.error("秒杀商品添加失败！");
        }

        // 将该秒杀商品的id存到商品数据库中，每个商品只能有一个秒杀商品
        goods.setSeckillId(seckillGoods.getId());
        res = goodsMapper.updateById(goods);
        if (res != 1) {
            return RespBean.error("秒杀商品添加失败！");
        }

        long diff = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Date now = simpleDateFormat.parse(timeController.getTime());
            diff = (seckillGoods.getEndDate().getTime() - now.getTime()) / 1000;
            if (diff <= 0) {
                return RespBean.error("秒杀商品结束时间不能小于当前时间！");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 添加秒杀商品缓存
        redisTemplate.opsForValue().set("SECKILL_GOODS_" + seckillGoods.getId(), seckillGoods, diff, TimeUnit.SECONDS);

        // 添加商品缓存
        redisTemplate.opsForValue().set("GOODS_" + goods.getId(), goods, diff, TimeUnit.SECONDS);

        // 添加秒杀商品的数量（将数量单独保存）
        stringRedisTemplate.opsForValue().set("SECKILL_GOODS_COUNT_" + seckillGoods.getId(), seckillGoods.getSeckillStock().toString(), diff, TimeUnit.SECONDS);

        return new RespBean(true, "秒杀商品添加成功并缓存成功!", null);
    }
}