-- =====================================================
-- FreeMall 商品服务数据库
-- =====================================================

CREATE DATABASE IF NOT EXISTS `freemall_product` 
DEFAULT CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE `freemall_product`;

-- -----------------------------------------------------
-- 商品分类表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `category` (
    `id` bigint(20) NOT NULL COMMENT '分类ID',
    `name` varchar(64) NOT NULL COMMENT '分类名称',
    `parent_id` bigint(20) DEFAULT '0' COMMENT '父分类ID，0表示顶级分类',
    `level` tinyint(4) NOT NULL DEFAULT '1' COMMENT '分类层级：1-一级，2-二级，3-三级',
    `sort` int(11) DEFAULT '0' COMMENT '排序',
    `icon` varchar(256) DEFAULT NULL COMMENT '分类图标',
    `status` tinyint(4) DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- -----------------------------------------------------
-- 商品品牌表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `brand` (
    `id` bigint(20) NOT NULL COMMENT '品牌ID',
    `name` varchar(64) NOT NULL COMMENT '品牌名称',
    `logo` varchar(256) DEFAULT NULL COMMENT '品牌Logo',
    `description` varchar(512) DEFAULT NULL COMMENT '品牌描述',
    `status` tinyint(4) DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品品牌表';

-- -----------------------------------------------------
-- 商品SPU表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product_spu` (
    `id` bigint(20) NOT NULL COMMENT 'SPU ID',
    `name` varchar(128) NOT NULL COMMENT '商品名称',
    `category_id` bigint(20) NOT NULL COMMENT '分类ID',
    `brand_id` bigint(20) DEFAULT NULL COMMENT '品牌ID',
    `merchant_id` bigint(20) NOT NULL COMMENT '商家ID',
    `main_image` varchar(256) DEFAULT NULL COMMENT '主图',
    `images` text COMMENT '图片列表JSON',
    `description` text COMMENT '商品描述',
    `status` tinyint(4) DEFAULT '0' COMMENT '状态：0-下架，1-上架，2-审核中',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_brand_id` (`brand_id`),
    KEY `idx_merchant_id` (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品SPU表';

-- -----------------------------------------------------
-- 商品SKU表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product_sku` (
    `id` bigint(20) NOT NULL COMMENT 'SKU ID',
    `spu_id` bigint(20) NOT NULL COMMENT 'SPU ID',
    `name` varchar(128) NOT NULL COMMENT 'SKU名称',
    `spec` varchar(256) DEFAULT NULL COMMENT '规格JSON',
    `price` decimal(10,2) NOT NULL COMMENT '售价',
    `original_price` decimal(10,2) DEFAULT NULL COMMENT '原价',
    `stock` int(11) DEFAULT '0' COMMENT '库存',
    `image` varchar(256) DEFAULT NULL COMMENT '主图',
    `status` tinyint(4) DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_spu_id` (`spu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品SKU表';
