-- 商家主表
CREATE TABLE IF NOT EXISTS `merchant` (
    `id` bigint(20) NOT NULL COMMENT 'ID',
    `username` varchar(64) NOT NULL COMMENT '用户名',
    `password` varchar(128) NOT NULL COMMENT '密码',
    `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
    `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
    `status` tinyint(4) DEFAULT '0' COMMENT '状态：0-待验证，1-已验证，2-待审核，3-已审核，4-已驳回，5-已禁用',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家账号表';

-- 商家资质表
CREATE TABLE IF NOT EXISTS `merchant_qualification` (
    `id` bigint(20) NOT NULL COMMENT 'ID',
    `merchant_id` bigint(20) NOT NULL COMMENT '商家ID',
    `company_name` varchar(128) NOT NULL COMMENT '公司名称',
    `business_license_url` varchar(256) NOT NULL COMMENT '营业执照图片地址',
    `tax_id` varchar(64) NOT NULL COMMENT '纳税人识别号',
    `legal_person` varchar(64) NOT NULL COMMENT '法人姓名',
    `contact_phone` varchar(20) NOT NULL COMMENT '联系电话',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_merchant_id` (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家资质信息表';
