package com.foxandgrapes.seckill.watch;

import com.foxandgrapes.seckill.service.impl.OrderServiceImpl;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZookeeperWatch implements Watcher {

    @Autowired
    private ZooKeeper zooKeeper;

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (Event.EventType.NodeDataChanged == watchedEvent.getType()) {
            try {
                String path = watchedEvent.getPath();
                String soldOutFlag = new String(zooKeeper.getData(path, true, new Stat()));
                if ("false".equals(soldOutFlag)) {
                    String seckillGoodsId = path.substring(path.lastIndexOf("/") + 1);
                    // 清除JVM中的标志
                    OrderServiceImpl.getProductSoldOutMap().remove(seckillGoodsId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
