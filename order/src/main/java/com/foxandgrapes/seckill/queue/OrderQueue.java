package com.foxandgrapes.seckill.queue;

import com.foxandgrapes.seckill.pojo.Order;
import com.foxandgrapes.seckill.service.IOrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderQueue {

    @Autowired
    private IOrderService orderService;

    @RabbitListener(queues = "order_queue")
    public void insertOrder(Order order) {

        System.out.println("order_queue接收消息：" + order);

        // 将队列中的订单写入数据库
        Map<String, Object> resultMap = orderService.insertOrder(order);

        if (!(Boolean) resultMap.get("result")){
            System.out.println("order_queue处理消息失败：");
        }

        System.out.println("order_queue处理消息成功！");
    }
}
