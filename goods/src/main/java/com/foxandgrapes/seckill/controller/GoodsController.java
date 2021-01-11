package com.foxandgrapes.seckill.controller;


import com.foxandgrapes.seckill.service.IGoodsService;
import com.foxandgrapes.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tsk
 * @since 2021-01-06
 */
@RestController
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    @RequestMapping("/getGoodsPage/{pageIndex}/{pageZise}")
    public RespBean getGoodsList(@PathVariable("pageIndex") Integer pageIndex,
                                 @PathVariable("pageZise") Integer pageZise) {
        return goodsService.getGoodsPage(pageIndex, pageZise);
    }

    @RequestMapping("/getGoods/{id}")
    public RespBean getGoods(@PathVariable("id") Long id) {
        return goodsService.getGoods(id);
    }
}