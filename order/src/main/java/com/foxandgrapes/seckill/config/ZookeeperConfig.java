package com.foxandgrapes.seckill.config;

import com.foxandgrapes.seckill.service.impl.OrderServiceImpl;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ZookeeperConfig {

    private ZooKeeper zooKeeper;

    @Bean(name = "zkClient")
    public ZooKeeper zkClient() throws IOException {
        zooKeeper = new ZooKeeper("192.168.182.129:2181", 60000, (event) -> {
            if (Watcher.Event.EventType.NodeDataChanged == event.getType()) {
                try {
                    String path = event.getPath();
                    String soldOutFlag = new String(zooKeeper.getData(path, true, new Stat()));
                    if ("false".equals(soldOutFlag)) {
                        String seckillGoodsId = path.substring(path.lastIndexOf("/") + 1);
                        // 清除JVM中的标志
                        OrderServiceImpl.getProductSoldOutMap().remove(Long.valueOf(seckillGoodsId));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return zooKeeper;
    }
}
