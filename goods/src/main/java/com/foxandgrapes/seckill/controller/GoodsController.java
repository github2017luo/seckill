package com.foxandgrapes.seckill.controller;


import com.foxandgrapes.seckill.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    @RequestMapping("/getGoodsList")
    public Map<String, Object> getGoodsList() {
        return goodsService.getGoodsList();
    }

    @RequestMapping("/getGoods/{id}")
    public Map<String, Object> getGoods(@PathVariable("id") Long id) {
        return goodsService.getGoods(id);
    }
}