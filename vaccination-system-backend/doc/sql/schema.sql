-- 疫苗接种管理系统数据库结构
-- 创建数据库
CREATE DATABASE IF NOT EXISTS vaccination_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE vaccination_system;

-- 用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码(BCrypt加密)',
  `phone` VARCHAR(20) COMMENT '手机号',
  `real_name` VARCHAR(50) COMMENT '真实姓名',
  `id_card` VARCHAR(18) COMMENT '身份证号',
  `role` TINYINT NOT NULL DEFAULT 0 COMMENT '角色:0-普通用户,1-医护人员,2-管理员',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态:0-正常,1-禁用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除:0-未删除,1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 家庭成员表
DROP TABLE IF EXISTS `family_member`;
CREATE TABLE `family_member` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '成员ID',
  `user_id` BIGINT NOT NULL COMMENT '所属用户ID',
  `name` VARCHAR(50) NOT NULL COMMENT '姓名',
  `id_card` VARCHAR(18) COMMENT '身份证号',
  `phone` VARCHAR(20) COMMENT '手机号',
  `birth_date` DATE COMMENT '出生日期',
  `gender` TINYINT DEFAULT 0 COMMENT '性别:0-男,1-女',
  `relation` VARCHAR(20) COMMENT '与用户关系:本人/子女/配偶等',
  `status` TINYINT DEFAULT 0 COMMENT '状态:0-正常,1-禁用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='家庭成员表';

-- 疫苗表
DROP TABLE IF EXISTS `vaccine`;
CREATE TABLE `vaccine` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '疫苗ID',
  `name` VARCHAR(100) NOT NULL COMMENT '疫苗名称',
  `code` VARCHAR(50) COMMENT '疫苗编码',
  `manufacturer` VARCHAR(100) COMMENT '生产厂家',
  `type` TINYINT DEFAULT 0 COMMENT '类型:0-免费,1-自费',
  `price` DECIMAL(10,2) DEFAULT 0 COMMENT '价格',
  `description` VARCHAR(500) COMMENT '疫苗说明',
  `status` TINYINT DEFAULT 0 COMMENT '状态:0-正常,1-禁用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='疫苗表';

-- 疫苗批次表
DROP TABLE IF EXISTS `vaccine_batch`;
CREATE TABLE `vaccine_batch` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '批次ID',
  `vaccine_id` BIGINT NOT NULL COMMENT '疫苗ID',
  `batch_no` VARCHAR(50) NOT NULL COMMENT '批次号',
  `production_date` DATE COMMENT '生产日期',
  `expiry_date` DATE COMMENT '有效期',
  `quantity` INT DEFAULT 0 COMMENT '库存数量',
  `used_quantity` INT DEFAULT 0 COMMENT '已使用数量',
  `status` TINYINT DEFAULT 0 COMMENT '状态:0-正常,1-临期(30天内),2-过期',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  KEY `idx_vaccine_id` (`vaccine_id`),
  KEY `idx_expiry_date` (`expiry_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='疫苗批次表';

-- 预约时段表
DROP TABLE IF EXISTS `appointment_slot`;
CREATE TABLE `appointment_slot` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '时段ID',
  `slot_date` DATE NOT NULL COMMENT '预约日期',
  `start_time` TIME NOT NULL COMMENT '开始时间',
  `end_time` TIME NOT NULL COMMENT '结束时间',
  `max_count` INT DEFAULT 20 COMMENT '最大预约数',
  `current_count` INT DEFAULT 0 COMMENT '当前预约数',
  `status` TINYINT DEFAULT 0 COMMENT '状态:0-可用,1-已满,2-禁用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  KEY `idx_slot_date` (`slot_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预约时段表';

-- 预约表
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '预约ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `family_member_id` BIGINT COMMENT '家庭成员ID(给孩子预约时使用)',
  `vaccine_id` BIGINT NOT NULL COMMENT '疫苗ID',
  `vaccine_batch_id` BIGINT COMMENT '疫苗批次ID',
  `appointment_date` DATE NOT NULL COMMENT '预约日期',
  `appointment_time` TIME NOT NULL COMMENT '预约时段',
  `status` TINYINT DEFAULT 0 COMMENT '状态:0-待接种,1-已接种,2-已取消,3-过期未接种',
  `remark` VARCHAR(200) COMMENT '备注',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_appointment_date` (`appointment_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预约表';

-- 接种记录表
DROP TABLE IF EXISTS `vaccination_record`;
CREATE TABLE `vaccination_record` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `appointment_id` BIGINT COMMENT '关联预约ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `family_member_id` BIGINT COMMENT '家庭成员ID',
  `vaccine_id` BIGINT NOT NULL COMMENT '疫苗ID',
  `vaccine_batch_id` BIGINT COMMENT '疫苗批次ID',
  `batch_no` VARCHAR(50) COMMENT '接种批次号',
  `injection_site` VARCHAR(20) COMMENT '接种部位',
  `doctor_id` BIGINT COMMENT '接种医生ID',
  `doctor_name` VARCHAR(50) COMMENT '接种医生姓名',
  `vaccination_time` DATETIME NOT NULL COMMENT '接种时间',
  `status` TINYINT DEFAULT 0 COMMENT '状态:0-正常,1-有不良反应',
  `adverse_reaction` VARCHAR(500) COMMENT '不良反应描述',
  `next_vaccination_date` VARCHAR(20) COMMENT '下次接种日期建议',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_family_member_id` (`family_member_id`),
  KEY `idx_vaccination_time` (`vaccination_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='接种记录表';