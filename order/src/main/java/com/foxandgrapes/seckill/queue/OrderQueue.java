package com.foxandgrapes.seckill.queue;

import com.foxandgrapes.seckill.service.IOrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderQueue {

    @Autowired
    private IOrderService orderService;

    @RabbitListener(queues = "order_queue")
    public void insertOrder(String msg) {

        System.out.println("order_queue接收消息："+msg);

        // 处理消息队列中的消息

    }
}
