package com.foxandgrapes.seckill.controller;


import com.foxandgrapes.seckill.pojo.SeckillGoods;
import com.foxandgrapes.seckill.service.ISeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tsk
 * @since 2021-01-06
 */
@RestController
public class SeckillGoodsController {

    @Autowired
    private ISeckillGoodsService seckillGoodsService;

    @RequestMapping("/insertSeckillGoods")
    public Map<String, Object> insertSeckillGoods(@RequestBody SeckillGoods seckillGoods) {
        return seckillGoodsService.insertSeckillGoods(seckillGoods);
    }
}
