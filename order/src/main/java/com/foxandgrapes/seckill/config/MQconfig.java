package com.foxandgrapes.seckill.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQconfig {

    public Queue queueOrder() {
        return new Queue("order_queue", true);
    }
}
