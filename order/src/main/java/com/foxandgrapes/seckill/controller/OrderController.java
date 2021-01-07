package com.foxandgrapes.seckill.controller;


import com.foxandgrapes.seckill.service.IOrderService;
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
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @RequestMapping("/increateOrder/{seckillGoodsId}/{userId}")
    public Map<String, Object> increateOrder(@PathVariable("seckillGoodsId") Long seckillGoodsId,
                                             @PathVariable("userId") Long userId) {
        return orderService.createOrder(seckillGoodsId, userId);
    }
}
