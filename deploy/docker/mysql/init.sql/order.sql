-- =====================================================
-- FreeMall 订单服务数据库
-- =====================================================

CREATE DATABASE IF NOT EXISTS `freemall_order` 
DEFAULT CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE `freemall_order`;

-- -----------------------------------------------------
-- 订单主表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `order` (
    `id` bigint(20) NOT NULL COMMENT '订单ID',
    `order_no` varchar(32) NOT NULL COMMENT '订单号',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `merchant_id` bigint(20) NOT NULL COMMENT '商家ID',
    `total_amount` decimal(10,2) NOT NULL COMMENT '订单总金额',
    `pay_amount` decimal(10,2) NOT NULL COMMENT '实付金额',
    `freight_amount` decimal(10,2) DEFAULT '0.00' COMMENT '运费',
    `discount_amount` decimal(10,2) DEFAULT '0.00' COMMENT '优惠金额',
    `status` tinyint(4) DEFAULT '0' COMMENT '订单状态：0-待支付，1-已支付，2-已发货，3-已完成，4-已取消，5-已退款',
    `receiver_name` varchar(64) NOT NULL COMMENT '收货人姓名',
    `receiver_phone` varchar(20) NOT NULL COMMENT '收货人电话',
    `receiver_province` varchar(32) DEFAULT NULL COMMENT '省',
    `receiver_city` varchar(32) DEFAULT NULL COMMENT '市',
    `receiver_district` varchar(32) DEFAULT NULL COMMENT '区',
    `receiver_address` varchar(256) NOT NULL COMMENT '详细地址',
    `remark` varchar(256) DEFAULT NULL COMMENT '订单备注',
    `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
    `ship_time` datetime DEFAULT NULL COMMENT '发货时间',
    `receive_time` datetime DEFAULT NULL COMMENT '收货时间',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_merchant_id` (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单主表';

-- -----------------------------------------------------
-- 订单商品项表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `order_item` (
    `id` bigint(20) NOT NULL COMMENT 'ID',
    `order_id` bigint(20) NOT NULL COMMENT '订单ID',
    `sku_id` bigint(20) NOT NULL COMMENT 'SKU ID',
    `spu_id` bigint(20) NOT NULL COMMENT 'SPU ID',
    `sku_name` varchar(128) NOT NULL COMMENT 'SKU名称',
    `sku_image` varchar(256) DEFAULT NULL COMMENT 'SKU图片',
    `price` decimal(10,2) NOT NULL COMMENT '商品单价',
    `quantity` int(11) NOT NULL COMMENT '购买数量',
    `total_amount` decimal(10,2) NOT NULL COMMENT '小计金额',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单商品项表';
