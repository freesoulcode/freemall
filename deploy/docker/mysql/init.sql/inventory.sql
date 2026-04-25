-- =====================================================
-- FreeMall 库存服务数据库
-- =====================================================

CREATE DATABASE IF NOT EXISTS `freemall_inventory` 
DEFAULT CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE `freemall_inventory`;

-- -----------------------------------------------------
-- 库存表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inventory` (
    `id` bigint(20) NOT NULL COMMENT 'ID',
    `sku_id` bigint(20) NOT NULL COMMENT 'SKU ID',
    `merchant_id` bigint(20) NOT NULL COMMENT '商家ID',
    `quantity` int(11) DEFAULT '0' COMMENT '可用库存数量',
    `frozen_quantity` int(11) DEFAULT '0' COMMENT '冻结库存数量',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_sku_id` (`sku_id`),
    KEY `idx_merchant_id` (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存表';

-- -----------------------------------------------------
-- 库存流水表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inventory_log` (
    `id` bigint(20) NOT NULL COMMENT 'ID',
    `sku_id` bigint(20) NOT NULL COMMENT 'SKU ID',
    `order_no` varchar(32) DEFAULT NULL COMMENT '订单号',
    `change_quantity` int(11) NOT NULL COMMENT '变动数量（正数增加，负数减少）',
    `type` tinyint(4) NOT NULL COMMENT '类型：1-下单扣减，2-支付成功，3-取消回滚，4-退款回滚',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_sku_id` (`sku_id`),
    KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存流水表';
