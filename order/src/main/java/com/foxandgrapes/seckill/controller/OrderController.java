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

    @RequestMapping("/increateOrder/{seckillGoodsId}")
    public Map<String, Object> increateOrder(@PathVariable("seckillGoodsId") Long seckillGoodsId,
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
}
