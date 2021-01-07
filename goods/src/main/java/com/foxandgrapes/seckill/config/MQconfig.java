package com.foxandgrapes.seckill.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQconfig {

    @Bean
    public Queue queueStock() {
        return new Queue("stock_queue", true);
    }
}
