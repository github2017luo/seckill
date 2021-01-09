package com.foxandgrapes.seckill.queue;

import com.foxandgrapes.seckill.service.IStockService;
import com.foxandgrapes.seckill.vo.RespBean;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockQueue {

    @Autowired
    private IStockService stockService;

    @RabbitListener(queues = "stock_queue")
    public void updateStock(Long seckillGoodsId) {
        System.out.println("stock_queue接受消息：" + seckillGoodsId);

        RespBean respBean = stockService.updateStock(seckillGoodsId, 0, 1);

        if (!respBean.getRet()) {
            System.out.println("stock_queue处理消息失败：" + respBean.getMsg());
        } else {
            System.out.println("stock_queue处理消息成功！");
        }

    }
}
