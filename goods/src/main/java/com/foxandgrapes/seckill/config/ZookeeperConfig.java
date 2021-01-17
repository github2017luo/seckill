package com.foxandgrapes.seckill.config;

import org.apache.zookeeper.ZooKeeper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ZookeeperConfig {

    private ZooKeeper zooKeeper;

    @Bean(name = "zkClient")
    public ZooKeeper zkClient() throws IOException {
        zooKeeper = new ZooKeeper("192.168.182.129:2181", 60000, (event) -> {
            System.out.println("回调函数触发了！");
        });
        return zooKeeper;
    }
}
