package com.foxandgrapes.seckill.controller;


import com.foxandgrapes.seckill.pojo.User;
import com.foxandgrapes.seckill.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
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

    @RequestMapping("/createOrder/{seckillGoodsId}")
    public Map<String, Object> createOrder(@PathVariable("seckillGoodsId") Long seckillGoodsId,
                                             HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");

        // 测试放行
        // if (user == null){
        //     resultMap.put("result", false);
        //     resultMap.put("msg", "用户没有登录不能购买！");
        //     return resultMap;
        // }
        // return orderService.createOrder(seckillGoodsId, user.getId());
        return orderService.createOrder(seckillGoodsId, 13729044632L);
    }

    @RequestMapping("/getOrder/{orderId}")
    public Map<String, Object> getOrder(@PathVariable("orderId") Long orderId,
                                        HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");

        // 测试放行
        // if (user == null){
        //     resultMap.put("result", false);
        //     resultMap.put("msg", "用户没有登录不能查询！");
        //     return resultMap;
        // }
        return orderService.getOrder(orderId);
    }

    @RequestMapping("/payOrder/{orderId}/{seckillGoodsId}")
    public Map<String, Object> payOrder(@PathVariable("orderId") Long orderId,
                                        @PathVariable("seckillGoodsId") Long seckillGoodsId,
                                        HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");

        // 测试放行
        // if (user == null){
        //     resultMap.put("result", false);
        //     resultMap.put("msg", "用户没有登录不能支付！");
        //     return resultMap;
        // }

        // 调用支付(模拟支付成功并返回正常数据)
        boolean isPay = true;

        if (!isPay){
            resultMap.put("result", false);
            resultMap.put("msg", "支付失败！");
            return resultMap;
        }

        return orderService.payOrder(orderId, seckillGoodsId);
    }
}
