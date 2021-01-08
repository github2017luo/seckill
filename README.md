# seckill 秒杀系统
    

# 系统环境
## Linux环境：centos7 [CentOS-7-x86_64-DVD-2009]<br>安装docker：yum install docker [Version:1.13.1]
## 在docker中拉取镜像以及创建容器：
    1、mysql [Server version: 8.0.22 MySQL Community Server - GPL]  端口映射 [3306->3306]
    2、redis [Redis server v=6.0.9] 端口映射 [6379->6379]
    3、rebbitmq [RabbitMQ 3.8.9 Erlang 23.2.1] 端口映射 [15672->15672, 5672->5672]

## 微服务对应端口
    server:         9000
    time-server:    8000
    goods:          7000
    user:           5000
    order:          4000

## 数据库建表语句
### 用户表
    CREATE TABLE `tb_user` (
      `id` bigint NOT NULL COMMENT '用户ID，手机号码',
      `password` varchar(32) DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt)+salt)',
      `salt` varchar(10) DEFAULT NULL COMMENT '第二次MD5的salt',
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8

### 商品表
    CREATE TABLE `tb_goods` (
      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品ID',
      `goods_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
      `goods_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
      `goods_img` varchar(64) DEFAULT NULL COMMENT '商品图片',
      `goods_detail` longtext COMMENT '商品详情',
      `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品价格',
      `goods_stock` int DEFAULT '0' COMMENT '商品库存，-1表示没有限制',
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8

### 订单表
    CREATE TABLE `tb_order` (
      `id` bigint NOT NULL COMMENT '订单ID',
      `user_id` bigint DEFAULT NULL COMMENT '用户ID',
      `goods_id` bigint DEFAULT NULL COMMENT '商品ID',
      `goods_name` varchar(16) DEFAULT NULL COMMENT '冗余过来的商品名称',
      `goods_count` int DEFAULT '0' COMMENT '商品数量',
      `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
      `status` tinyint DEFAULT '0' COMMENT '订单状态，0新建未支付，1已支付',
      `create_date` datetime DEFAULT NULL COMMENT '订单的创建时间',
      `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8

### 秒杀商品表
    CREATE TABLE `tb_seckill_goods` (
      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '秒杀商品ID',
      `goods_id` bigint DEFAULT NULL COMMENT '商品ID',
      `seckill_price` decimal(10,2) DEFAULT '0.00' COMMENT '秒杀价格',
      `stock_count` int DEFAULT NULL COMMENT '库存数量',
      `start_date` datetime DEFAULT NULL COMMENT '秒杀开始时间',
      `end_date` datetime DEFAULT NULL COMMENT '秒杀结束时间',
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8