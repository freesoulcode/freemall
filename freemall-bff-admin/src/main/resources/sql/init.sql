CREATE TABLE IF NOT EXISTS `admin_user` (
    `id` bigint(20) NOT NULL COMMENT 'ID',
    `username` varchar(64) NOT NULL COMMENT '用户名',
    `password` varchar(128) NOT NULL COMMENT '密码',
    `real_name` varchar(64) DEFAULT NULL COMMENT '真实姓名',
    `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
    `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
    `status` tinyint(4) DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员用户表';

-- 初始管理员账号: admin / admin123
INSERT INTO `admin_user` (`id`, `username`, `password`, `real_name`, `status`)
VALUES (1, 'admin', 'admin123', '系统管理员', 1)
ON DUPLICATE KEY UPDATE `username` = `username`;
