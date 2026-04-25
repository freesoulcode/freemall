-- =====================================================
-- FreeMall 支付服务数据库
-- =====================================================

CREATE DATABASE IF NOT EXISTS `freemall_payment` 
DEFAULT CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE `freemall_payment`;

-- -----------------------------------------------------
-- 支付流水表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment` (
    `id` bigint(20) NOT NULL COMMENT 'ID',
    `payment_no` varchar(32) NOT NULL COMMENT '支付流水号',
    `order_id` bigint(20) NOT NULL COMMENT '订单ID',
    `order_no` varchar(32) NOT NULL COMMENT '订单号',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `amount` decimal(10,2) NOT NULL COMMENT '支付金额',
    `pay_type` tinyint(4) NOT NULL COMMENT '支付方式：1-支付宝，2-微信，3-银行卡',
    `status` tinyint(4) DEFAULT '0' COMMENT '状态：0-待支付，1-支付成功，2-支付失败，3-已退款',
    `trade_no` varchar(64) DEFAULT NULL COMMENT '第三方交易号',
    `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_payment_no` (`payment_no`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_order_no` (`order_no`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付流水表';
