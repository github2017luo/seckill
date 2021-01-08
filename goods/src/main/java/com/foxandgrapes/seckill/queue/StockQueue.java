package com.foxandgrapes.seckill.queue;

import com.foxandgrapes.seckill.service.IStockService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StockQueue {

    @Autowired
    private IStockService stockService;

    @RabbitListener(queues = "stock_queue")
    public void updateStock(Long seckillGoodsId) {
        System.out.println("stock_queue接受消息：" + seckillGoodsId);

        Map<String, Object> resultMap = stockService.updateStock(seckillGoodsId, 0, 1);

        if (!(boolean) resultMap.get("result")) {
            System.out.println("stock_queue处理消息失败：" + resultMap.get("msg").toString());
        } else {
            System.out.println("stock_queue处理消息成功！");
        }

    }
}
