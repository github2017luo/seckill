package com.foxandgrapes.seckill.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxandgrapes.seckill.mapper.GoodsMapper;
import com.foxandgrapes.seckill.pojo.Goods;
import com.foxandgrapes.seckill.pojo.SeckillGoods;
import com.foxandgrapes.seckill.service.IGoodsService;
import com.foxandgrapes.seckill.service.ITimeController;
import com.foxandgrapes.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ITimeController timeController;

    @Override
    public RespBean getGoodsPage(Integer pageIndex, Integer pageSize) {

        // 分页查询
        IPage<Goods> page = new Page<>(pageIndex, pageSize);
        IPage<Goods> goodsPage = goodsMapper.selectPage(page, null);

        if (goodsPage == null || goodsPage.getRecords().size() == 0) {
            return RespBean.error( "商品信息获取失败！");
        }

        // 添加秒杀信息
        goodsPage.getRecords().forEach(goods -> getSeckillDetail(goods));

        return RespBean.success("商品列表获取成功！", goodsPage);
    }

    @Override
    public RespBean getGoods(Long id) {

        Goods goods = goodsMapper.selectById(id);
        if (goods == null) {
            return RespBean.error("查询无果！");
        }

        // 添加秒杀信息
        getSeckillDetail(goods);

        // 为了前端好展示而弄成列表返回
        List<Goods> list = new ArrayList<>();
        list.add(goods);
        return RespBean.success("商品获取成功！", list);
    }

    private void getSeckillDetail(Goods goods) {
        if (goods == null) return;

        // 取redis中的秒杀商品信息
        SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.opsForValue().get("SECKILL_GOODS_" + goods.getSeckillId());

        if (seckillGoods != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            try {
                Date now = simpleDateFormat.parse(timeController.getTime());

                // 在秒杀结束时间前
                if (now.getTime() <= seckillGoods.getEndDate().getTime()) {
                    // 添加秒杀的信息
                    goods.setSeckillPrice(seckillGoods.getSeckillPrice());
                    // redis中的库存数是固定的，没有实时刷新，所以不能从这里读取
                    // 直接从redis中独自保存的库存中读取
                    goods.setSeckillStock(Integer.valueOf(stringRedisTemplate.opsForValue().get("SECKILL_GOODS_COUNT_" + goods.getSeckillId())));
                    goods.setStartDate(seckillGoods.getStartDate());
                    goods.setEndDate(seckillGoods.getEndDate());
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
