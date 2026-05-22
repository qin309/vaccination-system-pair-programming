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
INSERT INTO vaccine (name, code, manufacturer, type, price, description, status) VALUES ('狂犬疫苗', 'RABIES-001', '广州诺诚', 1, 280, '狂犬病疫苗', 0);
INSERT INTO vaccine (name, code, manufacturer, type, price, description, status) VALUES ('麻疹疫苗', 'MEASLES-001', '武汉生物', 0, 0, '麻疹减毒活疫苗', 0);
INSERT INTO vaccine (name, code, manufacturer, type, price, description, status) VALUES ('甲肝疫苗', 'HA-001', '浙江普康', 1, 120, '甲型肝炎疫苗', 0);
INSERT INTO vaccine (name, code, manufacturer, type, price, description, status) VALUES ('肺炎疫苗', 'PNEUMO-001', '辉瑞', 1, 200, '13价肺炎球菌疫苗', 0);
INSERT INTO vaccine (name, code, manufacturer, type, price, description, status) VALUES ('水痘疫苗', 'VARICELLA-001', '长春长生', 1, 150, '水痘减毒活疫苗', 0);

-- 插入测试批次数据
INSERT INTO vaccine_batch (vaccine_id, batch_no, production_date, expiry_date, quantity, used_quantity, status) VALUES (1, 'B2024001', '2024-01-01', '2025-12-31', 100, 5, 0);
INSERT INTO vaccine_batch (vaccine_id, batch_no, production_date, expiry_date, quantity, used_quantity, status) VALUES (2, 'B2024002', '2024-02-01', '2025-06-30', 50, 10, 0);
INSERT INTO vaccine_batch (vaccine_id, batch_no, production_date, expiry_date, quantity, used_quantity, status) VALUES (3, 'B2024003', '2024-03-01', '2025-09-30', 80, 8, 0);
INSERT INTO vaccine_batch (vaccine_id, batch_no, production_date, expiry_date, quantity, used_quantity, status) VALUES (4, 'B2024004', '2024-04-01', '2025-08-31', 60, 12, 0);
INSERT INTO vaccine_batch (vaccine_id, batch_no, production_date, expiry_date, quantity, used_quantity, status) VALUES (5, 'B2024005', '2024-05-01', '2025-07-31', 100, 3, 0);
INSERT INTO vaccine_batch (vaccine_id, batch_no, production_date, expiry_date, quantity, used_quantity, status) VALUES (6, 'B2024006', '2024-06-01', '2025-10-31', 40, 5, 0);
INSERT INTO vaccine_batch (vaccine_id, batch_no, production_date, expiry_date, quantity, used_quantity, status) VALUES (7, 'B2024007', '2024-07-01', '2025-11-30', 30, 2, 0);
INSERT INTO vaccine_batch (vaccine_id, batch_no, production_date, expiry_date, quantity, used_quantity, status) VALUES (8, 'B2024008', '2024-08-01', '2025-12-31', 50, 1, 0);

-- 插入测试家庭成员数据 (test用户id=2)
INSERT INTO family_member (user_id, name, id_card, phone, birth_date, gender, relation, status) VALUES (2, '张小明', '110101202001010001', '13900000001', '2020-01-01', 0, '父女', 0);
INSERT INTO family_member (user_id, name, id_card, phone, birth_date, gender, relation, status) VALUES (2, '李小红', '110101199005150002', '13900000002', '1990-05-15', 1, '母女', 0);

-- 插入接种记录数据 (test用户id=2的家庭成员)
INSERT INTO vaccination_record (appointment_id, user_id, family_member_id, vaccine_id, vaccine_batch_id, batch_no, injection_site, doctor_id, doctor_name, vaccination_time, status, adverse_reaction, next_vaccination_date) VALUES
(null, 2, 1, 1, 1, 'B2024001', '左上臂', 1, '王医生', '2026-05-10 09:30:00', 0, null, '2026-06-10'),
(null, 2, 1, 3, 3, 'B2024003', '右上臂', 1, '王医生', '2026-05-08 10:00:00', 0, null, '2026-06-08'),
(null, 2, 2, 2, 2, 'B2024002', '左上臂', 2, '李医生', '2026-05-05 14:30:00', 0, null, null),
(null, 2, 1, 5, 5, 'B2024005', '右上臂', 1, '王医生', '2026-04-20 08:00:00', 0, null, '2026-05-20'),
(null, 2, 2, 1, 1, 'B2024001', '左上臂', 2, '李医生', '2026-04-15 11:00:00', 1, '轻微发热', null),
(null, 2, 1, 4, 4, 'B2024004', '右上臂', 1, '王医生', '2026-04-10 15:00:00', 0, null, '2026-05-10'),
(null, 2, 2, 6, 6, 'B2024006', '左上臂', 2, '李医生', '2026-03-25 09:00:00', 0, null, null),
(null, 2, 1, 2, 2, 'B2024002', '右上臂', 1, '王医生', '2026-03-20 10:30:00', 0, null, '2026-04-20');

-- 插入预约时段数据 (未来5天，每天7个时段)
INSERT INTO appointment_slot (slot_date, start_time, end_time, max_count, current_count, status) VALUES
('2026-05-23', '08:00:00', '09:00:00', 20, 0, 0),
('2026-05-23', '09:00:00', '10:00:00', 20, 0, 0),
('2026-05-23', '10:00:00', '11:00:00', 20, 0, 0),
('2026-05-23', '11:00:00', '12:00:00', 20, 0, 0),
('2026-05-23', '14:00:00', '15:00:00', 20, 0, 0),
('2026-05-23', '15:00:00', '16:00:00', 20, 0, 0),
('2026-05-23', '16:00:00', '17:00:00', 20, 0, 0),
('2026-05-24', '08:00:00', '09:00:00', 20, 0, 0),
('2026-05-24', '09:00:00', '10:00:00', 20, 0, 0),
('2026-05-24', '10:00:00', '11:00:00', 20, 0, 0),
('2026-05-24', '11:00:00', '12:00:00', 20, 0, 0),
('2026-05-24', '14:00:00', '15:00:00', 20, 0, 0),
('2026-05-24', '15:00:00', '16:00:00', 20, 0, 0),
('2026-05-24', '16:00:00', '17:00:00', 20, 0, 0),
('2026-05-25', '08:00:00', '09:00:00', 20, 0, 0),
('2026-05-25', '09:00:00', '10:00:00', 20, 0, 0),
('2026-05-25', '10:00:00', '11:00:00', 20, 0, 0),
('2026-05-25', '11:00:00', '12:00:00', 20, 0, 0),
('2026-05-25', '14:00:00', '15:00:00', 20, 0, 0),
('2026-05-25', '15:00:00', '16:00:00', 20, 0, 0),
('2026-05-25', '16:00:00', '17:00:00', 20, 0, 0),
('2026-05-26', '08:00:00', '09:00:00', 20, 0, 0),
('2026-05-26', '09:00:00', '10:00:00', 20, 0, 0),
('2026-05-26', '10:00:00', '11:00:00', 20, 0, 0),
('2026-05-26', '14:00:00', '15:00:00', 20, 0, 0),
('2026-05-26', '15:00:00', '16:00:00', 20, 0, 0),
('2026-05-27', '08:00:00', '09:00:00', 20, 0, 0),
('2026-05-27', '09:00:00', '10:00:00', 20, 0, 0),
('2026-05-27', '10:00:00', '11:00:00', 20, 0, 0),
('2026-05-27', '14:00:00', '15:00:00', 20, 0, 0);