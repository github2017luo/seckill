package com.foxandgrapes.seckill.controller;


import com.foxandgrapes.seckill.pojo.User;
import com.foxandgrapes.seckill.service.IOrderService;
import com.foxandgrapes.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tsk
 * @since 2021-01-06
 */
@RestController
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @RequestMapping("/createOrder/{seckillGoodsId}")
    public RespBean createOrder(@PathVariable("seckillGoodsId") Long seckillGoodsId,
                                HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // 测试放行
        if (user == null){
            return RespBean.error("用户没有登录不能购买！");
        }

        return orderService.createOrder(seckillGoodsId, user.getId());
        // return orderService.createOrder(seckillGoodsId, 13729044632L);
    }

    @RequestMapping("/getOrder/{orderId}")
    public RespBean getOrder(@PathVariable("orderId") Long orderId,
                                        HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // 测试放行
        if (user == null){
            return RespBean.error("用户没有登录不能查询！");
        }
        return orderService.getOrder(orderId);
    }

    @RequestMapping("/payOrder/{orderId}/{seckillGoodsId}")
    public RespBean payOrder(@PathVariable("orderId") Long orderId,
                                        @PathVariable("seckillGoodsId") Long seckillGoodsId,
                                        HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // 测试放行
        if (user == null){
            return RespBean.error("用户没有登录不能支付！");
        }

        // 调用支付
        boolean isPay = pay();

        if (!isPay){
            return RespBean.error("支付失败！");
        }

        return orderService.payOrder(orderId, seckillGoodsId);
    }

    private boolean pay() {
        // 模拟支付成功
        return true;
    }
}
