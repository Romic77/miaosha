create database IF NOT EXISTS `miaosha_user` default character set utf8 collate utf8_bin;
create database IF NOT EXISTS `miaosha_order` default character set utf8 collate utf8_bin;
create database IF NOT EXISTS `miaosha_product` default character set utf8 collate utf8_bin;

USE `miaosha_user`;
CREATE TABLE `t_user` (
                          `user_id` bigint(20) NOT NULL COMMENT '主键',
                          `username` varchar(255)  DEFAULT NULL,
                          `login_password` varchar(255) DEFAULT NULL COMMENT '登陆密码',
                          `balance` decimal(15,2) DEFAULT '0.00' COMMENT '用户余额',
                          `status` int(1) DEFAULT NULL COMMENT '状态 1 正常 0 无效',
                          `user_regtime` datetime DEFAULT NULL COMMENT '注册时间',
                          PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

drop table IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `branch_id` bigint(20) NOT NULL,
                            `xid` varchar(100) NOT NULL,
                            `context` varchar(128) NOT NULL,
                            `rollback_info` longblob NOT NULL,
                            `log_status` int(11) NOT NULL,
                            `log_created` datetime NOT NULL,
                            `log_modified` datetime NOT NULL,
                            `ext` varchar(100) DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO `t_user`(`user_id`, `username`, `login_password`, `balance`,`status`, `user_regtime`) VALUES (1, 'zhangsan', '123456',666666.66, 1, '2021-02-28 22:24:01');

USE `miaosha_product`;
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


drop table IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `branch_id` bigint(20) NOT NULL,
                            `xid` varchar(100) NOT NULL,
                            `context` varchar(128) NOT NULL,
                            `rollback_info` longblob NOT NULL,
                            `log_status` int(11) NOT NULL,
                            `log_created` datetime NOT NULL,
                            `log_modified` datetime NOT NULL,
                            `ext` varchar(100) DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `t_product`(`product_id`, `product_name`, `product_price`, `content`,`total_stocks`, `create_time`) VALUES (2001, '电脑', 5000.00,'支持LOL',1000, '2021-02-28 22:24:01');

USE `miaosha_order`;
CREATE TABLE `t_order` (
                           `order_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单ID',
                           `product_id` bigint(20)  NOT NULL COMMENT '产品ID',
                           `product_name` varchar(1000) NOT NULL DEFAULT '' COMMENT '产品名称',
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

drop table IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `branch_id` bigint(20) NOT NULL,
                            `xid` varchar(100) NOT NULL,
                            `context` varchar(128) NOT NULL,
                            `rollback_info` longblob NOT NULL,
                            `log_status` int(11) NOT NULL,
                            `log_created` datetime NOT NULL,
                            `log_modified` datetime NOT NULL,
                            `ext` varchar(100) DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

