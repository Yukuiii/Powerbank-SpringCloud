CREATE TABLE `order` (
  `id` varchar(32) NOT NULL COMMENT '订单ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `device_id` varchar(32) NOT NULL COMMENT '设备ID',
  `status` tinyint NOT NULL COMMENT '订单状态(0:未支付 1:已支付 2:使用中 3:已完成 4:已取消)',
  `amount` decimal(10,2) NOT NULL COMMENT '订单金额',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `borrow_time` datetime DEFAULT NULL COMMENT '借出时间',
  `return_time` datetime DEFAULT NULL COMMENT '归还时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_device_id` (`device_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表'; 