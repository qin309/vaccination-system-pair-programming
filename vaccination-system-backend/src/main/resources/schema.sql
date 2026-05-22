-- H2数据库初始化脚本

-- 用户表
CREATE TABLE IF NOT EXISTS sys_user (
  id BIGINT AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  phone VARCHAR(20),
  real_name VARCHAR(50),
  id_card VARCHAR(18),
  role INT NOT NULL DEFAULT 0,
  status INT NOT NULL DEFAULT 0,
  login_fail_count INT DEFAULT 0,
  lock_time TIMESTAMP,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted INT DEFAULT 0,
  PRIMARY KEY (id)
);

-- 疫苗表
CREATE TABLE IF NOT EXISTS vaccine (
  id BIGINT AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  code VARCHAR(50),
  manufacturer VARCHAR(100),
  type INT DEFAULT 0,
  price DECIMAL(10,2) DEFAULT 0,
  description VARCHAR(500),
  status INT DEFAULT 0,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted INT DEFAULT 0,
  PRIMARY KEY (id)
);

-- 疫苗批次表
CREATE TABLE IF NOT EXISTS vaccine_batch (
  id BIGINT AUTO_INCREMENT,
  vaccine_id BIGINT NOT NULL,
  batch_no VARCHAR(50) NOT NULL,
  production_date DATE,
  expiry_date DATE,
  quantity INT DEFAULT 0,
  used_quantity INT DEFAULT 0,
  status INT DEFAULT 0,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted INT DEFAULT 0,
  PRIMARY KEY (id)
);

-- 家庭成员表
CREATE TABLE IF NOT EXISTS family_member (
  id BIGINT AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  name VARCHAR(50) NOT NULL,
  id_card VARCHAR(18),
  phone VARCHAR(20),
  birth_date DATE,
  gender INT DEFAULT 0,
  relation VARCHAR(20),
  status INT DEFAULT 0,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted INT DEFAULT 0,
  PRIMARY KEY (id)
);

-- 预约时段表
CREATE TABLE IF NOT EXISTS appointment_slot (
  id BIGINT AUTO_INCREMENT,
  slot_date DATE NOT NULL,
  start_time TIME NOT NULL,
  end_time TIME NOT NULL,
  max_count INT DEFAULT 20,
  current_count INT DEFAULT 0,
  status INT DEFAULT 0,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted INT DEFAULT 0,
  PRIMARY KEY (id)
);

-- 预约表
CREATE TABLE IF NOT EXISTS appointment (
  id BIGINT AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  family_member_id BIGINT,
  vaccine_id BIGINT NOT NULL,
  vaccine_batch_id BIGINT,
  appointment_date DATE NOT NULL,
  appointment_time TIME NOT NULL,
  status INT DEFAULT 0,
  remark VARCHAR(200),
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted INT DEFAULT 0,
  PRIMARY KEY (id)
);

-- 接种记录表
CREATE TABLE IF NOT EXISTS vaccination_record (
  id BIGINT AUTO_INCREMENT,
  appointment_id BIGINT,
  user_id BIGINT NOT NULL,
  family_member_id BIGINT,
  vaccine_id BIGINT NOT NULL,
  vaccine_batch_id BIGINT,
  batch_no VARCHAR(50),
  injection_site VARCHAR(20),
  doctor_id BIGINT,
  doctor_name VARCHAR(50),
  vaccination_time TIMESTAMP NOT NULL,
  status INT DEFAULT 0,
  adverse_reaction VARCHAR(500),
  next_vaccination_date VARCHAR(20),
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted INT DEFAULT 0,
  PRIMARY KEY (id)
);

-- 插入测试数据：管理员账号 (密码: 123456)
INSERT INTO sys_user (username, password, phone, real_name, role, status) VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '13800138000', '管理员', 2, 0);

-- 插入测试数据：普通用户账号 (密码: 123456)
INSERT INTO sys_user (username, password, phone, real_name, role, status) VALUES ('test', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '13900139000', '测试用户', 0, 0);

-- 插入测试疫苗数据
INSERT INTO vaccine (name, code, manufacturer, type, price, description, status) VALUES ('新冠疫苗', 'COVID-001', '北京生物', 0, 0, '新冠病毒疫苗', 0);
INSERT INTO vaccine (name, code, manufacturer, type, price, description, status) VALUES ('流感疫苗', 'FLU-001', '华兰生物', 1, 80, '流行性感冒疫苗', 0);
INSERT INTO vaccine (name, code, manufacturer, type, price, description, status) VALUES ('乙肝疫苗', 'HBV-001', '天坛生物', 0, 0, '乙型肝炎疫苗', 0);

-- 插入测试批次数据
INSERT INTO vaccine_batch (vaccine_id, batch_no, production_date, expiry_date, quantity, used_quantity, status) VALUES (1, 'B2024001', '2024-01-01', '2025-12-31', 100, 0, 0);
INSERT INTO vaccine_batch (vaccine_id, batch_no, production_date, expiry_date, quantity, used_quantity, status) VALUES (2, 'B2024002', '2024-02-01', '2025-06-30', 50, 0, 0);