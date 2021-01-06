package com.foxandgrapes.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxandgrapes.seckill.mapper.GoodsMapper;
import com.foxandgrapes.seckill.pojo.Goods;
import com.foxandgrapes.seckill.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Map<String, Object> getGoodsList() {
        Map<String, Object> map = new HashMap<>();
        List<Goods> goodsList = goodsMapper.selectList(null);
        if (goodsList == null || goodsList.size() == 0) {
            map.put("result", "false");
            map.put("msg", "商品信息获取失败！");
            return map;
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
        map.put("result", "true");
        map.put("goods", goods);
        return map;
    }
}
