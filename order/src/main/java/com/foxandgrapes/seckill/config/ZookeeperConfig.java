package com.foxandgrapes.seckill.config;

import com.foxandgrapes.seckill.watch.ZookeeperWatch;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ZookeeperConfig {

    @Value("192.168.182.129")
    private String connectString;

    @Value("50000")
    private int timeout;

    @Autowired
    private ZookeeperWatch zookeeperWatch;

    @Bean(name = "zkClient")
    public ZooKeeper zkClient() throws IOException {
        return new ZooKeeper(connectString, timeout, zookeeperWatch);
    }
}
