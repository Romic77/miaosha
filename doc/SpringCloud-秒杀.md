# SpringCloud-秒杀

## 表设计

表设计参考

https://gitee.com/gz-yami/mall4j/blob/master/db/yami_shop.sql

首先设计表结构

- 用户表

```mysql
CREATE TABLE `t_user` (
  `user_id` bigint(20) NOT NULL COMMENT '主键',
  `username` varchar(255)  DEFAULT NULL,
  `login_password` varchar(255) DEFAULT NULL COMMENT '登陆密码',
  `status` int(1) DEFAULT NULL COMMENT '状态 1 正常 0 无效',
  `user_regtime` datetime DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
```

- 商品表

```mysql
CREATE TABLE `t_product` (
  `product_id` bigint(20)  NOT NULL  COMMENT '产品ID',
  `product_name` varchar(300) NOT NULL DEFAULT '' COMMENT '商品名称',
  `product_price` decimal(15,2) DEFAULT '0.00' COMMENT '商品价格',
  `content` text COMMENT '商品描述',
  `total_stocks` int(11) DEFAULT '0' COMMENT '总库存',
  `create_time` datetime DEFAULT NULL COMMENT '录入时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
   PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8 COMMENT='商品';
```

- 订单表

```mysql
CREATE TABLE `t_order` (
  `order_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `product_id` bigint(20)  NOT NULL COMMENT '产品ID',
  `prod_name` varchar(1000) NOT NULL DEFAULT '' COMMENT '产品名称',
  `user_id` varchar(36) NOT NULL COMMENT '订购用户ID',
  `total` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '总值',
  `remarks` varchar(1024) DEFAULT NULL COMMENT '订单备注',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '订单状态 1:待付款 2:待发货 3:待收货 4:待评价 5:成功 6:失败',
  `product_nums` int(10) DEFAULT NULL COMMENT '订单商品总数',
  `create_time` datetime NOT NULL COMMENT '订购时间',
  `update_time` datetime DEFAULT NULL COMMENT '订单更新时间',
  `pay_time` datetime DEFAULT NULL COMMENT '付款时间',
  `dvy_time` datetime DEFAULT NULL COMMENT '发货时间',
  `finally_time` datetime DEFAULT NULL COMMENT '完成时间',
  `cancel_time` datetime DEFAULT NULL COMMENT '取消时间',
  `is_payed` tinyint(1) DEFAULT NULL COMMENT '是否已经支付，1：已经支付过，0：，没有支付过',
  `delete_status` int(1) DEFAULT '0' COMMENT '用户订单删除状态，0：没有删除， 1：回收站， 2：永久删除',
  `refund_sts` int(1) DEFAULT '0' COMMENT '0:默认,1:在处理,2:处理完成',
  `order_type` tinyint(2) DEFAULT NULL COMMENT '订单类型',
  `close_type` tinyint(2) DEFAULT NULL COMMENT '订单关闭原因 1-超时未支付 2-退款关闭 4-买家取消 15-已通过货到付款交易',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='订单表';
```

## 分布式ID生成-leaf

> product/order表的id都是使用leaf生成

### leaf服务配置

### Leaf Server

我们提供了一个基于spring boot的HTTP服务来获取ID

#### 配置介绍

Leaf 提供两种生成的ID的方式（号段模式和snowflake模式），你可以同时开启两种方式，也可以指定开启某种方式（默认两种方式为关闭状态）。

Leaf Server的配置都在leaf-server/src/main/resources/leaf.properties中

| 配置项                    | 含义                          | 默认值 |
| ------------------------- | ----------------------------- | ------ |
| leaf.name                 | leaf 服务名                   |        |
| leaf.segment.enable       | 是否开启号段模式              | false  |
| leaf.jdbc.url             | mysql 库地址                  |        |
| leaf.jdbc.username        | mysql 用户名                  |        |
| leaf.jdbc.password        | mysql 密码                    |        |
| leaf.snowflake.enable     | 是否开启snowflake模式         | false  |
| leaf.snowflake.zk.address | snowflake模式下的zk地址       |        |
| leaf.snowflake.port       | snowflake模式下的服务注册端口 |        |

#### 号段模式

如果使用号段模式，需要建立DB表，并配置leaf.jdbc.url, leaf.jdbc.username, leaf.jdbc.password

如果不想使用该模式配置leaf.segment.enable=false即可。

##### 创建数据表

```
CREATE DATABASE leaf
CREATE TABLE `leaf_alloc` (
  `biz_tag` varchar(128)  NOT NULL DEFAULT '',
  `max_id` bigint(20) NOT NULL DEFAULT '1',
  `step` int(11) NOT NULL,
  `description` varchar(256)  DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`biz_tag`)
) ENGINE=InnoDB;

insert into leaf_alloc(biz_tag, max_id, step, description) values('leaf-segment-order', 1, 2000, 'Test leaf Segment Mode Get Id')
```

##### 配置相关数据项

在leaf.properties中配置leaf.jdbc.url, leaf.jdbc.username, leaf.jdbc.password参数

#### Snowflake模式

算法取自twitter开源的snowflake算法。

如果不想使用该模式配置leaf.snowflake.enable=false即可。

##### 配置zookeeper地址

在leaf.properties中配置leaf.snowflake.zk.address，配置leaf 服务监听的端口leaf.snowflake.port。

#### 运行Leaf Server

##### 打包服务

```
git clone git@github.com:Meituan-Dianping/Leaf.git
//按照上面的号段模式在工程里面配置好
cd leaf
mvn clean install -DskipTests
cd leaf-server
```

##### 运行服务

*注意:首先得先配置好数据库表或者zk地址*

###### mvn方式

```
mvn spring-boot:run
```

###### 脚本方式

```
sh deploy/run.sh
```

##### 测试

```
#segment
curl http://localhost:8080/api/segment/get/leaf-segment-test
#snowflake
curl http://localhost:8080/api/snowflake/get/test
```

##### 监控页面

号段模式：http://localhost:8080/cache

### Leaf Core

当然，为了追求更高的性能，需要通过RPC Server来部署Leaf 服务，那仅需要引入leaf-core的包，把生成ID的API封装到指定的RPC框架中即可。

### 注意事项

注意现在leaf使用snowflake模式的情况下 其获取ip的逻辑直接取首个网卡ip【特别对于会更换ip的服务要注意】避免浪费workId



## nacos配置中心

- nacos 单机启动服务端-nacos-server-1.4.1
  - startup.cmd -m standalone

## xxl-job分布式调度中心





| 应用               | 端口 |
| ------------------ | ---- |
| leaf-server        | 8081 |
| sentinel-dashboard | 8082 |
| xxl-job-admin      | 8084 |

