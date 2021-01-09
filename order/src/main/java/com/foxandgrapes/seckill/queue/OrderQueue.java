package com.foxandgrapes.seckill.queue;

import com.foxandgrapes.seckill.pojo.Order;
import com.foxandgrapes.seckill.service.IOrderService;
import com.foxandgrapes.seckill.vo.RespBean;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderQueue {

    @Autowired
    private IOrderService orderService;

    @RabbitListener(queues = "order_queue")
    public void insertOrder(Order order) {

        System.out.println("order_queue接收消息：" + order);

        // 将队列中的订单写入数据库
        RespBean respBean = orderService.insertOrder(order);

        if (!respBean.getRet()){
            System.out.println("order_queue处理消息失败：" + respBean.getMsg());
        } else {
            System.out.println("order_queue处理消息成功！");
        }
    }
}
