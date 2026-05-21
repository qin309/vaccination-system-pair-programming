-- 疫苗接种管理系统初始化数据
USE vaccination_system;

-- 插入管理员账号
INSERT INTO `user` (`username`, `password`, `phone`, `real_name`, `id_card`, `role`, `status`) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '13800138000', '系统管理员', '110101199001011234', 2, 0);

-- 插入医护人员账号
INSERT INTO `user` (`username`, `password`, `phone`, `real_name`, `id_card`, `role`, `status`) VALUES
('doctor001', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '13800138001', '张医生', '110101198501011234', 1, 0),
('doctor002', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '13800138002', '李医生', '110101198601011234', 1, 0);

-- 插入普通用户账号
INSERT INTO `user` (`username`, `password`, `phone`, `real_name`, `id_card`, `role`, `status`) VALUES
('user001', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '13900139001', '王小明', '110101200001011234', 0, 0),
('user002', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '13900139002', '赵小红', '110101200001011235', 0, 0);

-- 插入家庭成员数据
INSERT INTO `family_member` (`user_id`, `name`, `id_card`, `phone`, `birth_date`, `gender`, `relation`, `status`) VALUES
(3, '王小明', '110101200001011234', '13900139001', '2000-01-01', 0, '本人', 0),
(3, '王小宝', '110101201801011234', NULL, '2018-01-01', 0, '子女', 0),
(4, '赵小红', '110101200001011235', '13900139002', '2000-01-01', 1, '本人', 0);

-- 插入疫苗数据
INSERT INTO `vaccine` (`name`, `code`, `manufacturer`, `type`, `price`, `description`, `status`) VALUES
('乙肝疫苗(第一针)', 'HBV-1', '北京生物制品研究所', 0, 0.00, '新生儿出生后24小时内接种，预防乙型肝炎', 0),
('乙肝疫苗(第二针)', 'HBV-2', '北京生物制品研究所', 0, 0.00, '出生后1个月接种', 0),
('乙肝疫苗(第三针)', 'HBV-3', '北京生物制品研究所', 0, 0.00, '出生后6个月接种', 0),
('卡介苗', 'BCG', '成都生物制品研究所', 0, 0.00, '预防结核病，出生后24小时内接种', 0),
('脊灰疫苗(第一针)', 'IPV-1', '北京生物制品研究所', 0, 0.00, '预防脊髓灰质炎，2月龄接种', 0),
('脊灰疫苗(第二针)', 'IPV-2', '北京生物制品研究所', 0, 0.00, '3月龄接种', 0),
('百白破疫苗(第一针)', 'DTP-1', '武汉生物制品研究所', 0, 0.00, '预防百日咳、白喉、破伤风，3月龄接种', 0),
('麻疹疫苗', 'MV', '上海生物制品研究所', 0, 0.00, '预防麻疹，8月龄接种', 0),
('流感疫苗', 'FLU', '华兰生物', 1, 80.00, '预防季节性流感，每年接种', 0),
('HPV疫苗', 'HPV', '默沙东', 1, 300.00, '预防宫颈癌，适用于9-45岁女性', 0);

-- 插入疫苗批次数据
INSERT INTO `vaccine_batch` (`vaccine_id`, `batch_no`, `production_date`, `expiry_date`, `quantity`, `used_quantity`, `status`) VALUES
(1, 'HBV2024001', '2024-01-01', '2025-12-31', 500, 0, 0),
(2, 'HBV2024002', '2024-01-01', '2025-12-31', 500, 50, 0),
(3, 'HBV2024003', '2024-01-01', '2025-12-31', 500, 100, 0),
(4, 'BCG2024001', '2024-02-01', '2025-02-01', 300, 30, 0),
(5, 'IPV2024001', '2024-01-15', '2025-01-15', 400, 40, 0),
(9, 'FLU2024001', '2024-09-01', '2025-03-01', 200, 20, 0),
(10, 'HPV2024001', '2024-03-01', '2025-03-01', 100, 10, 0);

-- 插入预约时段数据
INSERT INTO `appointment_slot` (`slot_date`, `start_time`, `end_time`, `max_count`, `current_count`, `status`) VALUES
('2024-11-01', '08:00:00', '08:30:00', 20, 5, 0),
('2024-11-01', '08:30:00', '09:00:00', 20, 0, 0),
('2024-11-01', '09:00:00', '09:30:00', 20, 3, 0),
('2024-11-01', '09:30:00', '10:00:00', 20, 0, 0),
('2024-11-01', '10:00:00', '10:30:00', 20, 8, 1),
('2024-11-02', '08:00:00', '08:30:00', 20, 0, 0),
('2024-11-02', '08:30:00', '09:00:00', 20, 0, 0),
('2024-11-02', '09:00:00', '09:30:00', 20, 0, 0),
('2024-11-02', '09:30:00', '10:00:00', 20, 0, 0),
('2024-11-02', '10:00:00', '10:30:00', 20, 0, 0);

-- 插入预约数据
INSERT INTO `appointment` (`user_id`, `family_member_id`, `vaccine_id`, `vaccine_batch_id`, `appointment_date`, `appointment_time`, `status`, `remark`) VALUES
(3, 2, 5, 5, '2024-11-01', '08:00:00', 0, '给孩子接种脊灰疫苗'),
(3, NULL, 9, 6, '2024-11-01', '09:00:00', 0, '接种流感疫苗'),
(4, NULL, 10, 7, '2024-11-02', '08:30:00', 1, '接种HPV疫苗第一针');

-- 插入接种记录数据
INSERT INTO `vaccination_record` (`appointment_id`, `user_id`, `family_member_id`, `vaccine_id`, `vaccine_batch_id`, `batch_no`, `injection_site`, `doctor_id`, `doctor_name`, `vaccination_time`, `status`, `next_vaccination_date`) VALUES
(3, 4, NULL, 10, 7, 'HPV2024001', '左上臂三角肌', 1, '张医生', '2024-11-02 08:35:00', 0, '2025-02-02');

-- 密码说明: 所有初始密码为 123456，BCrypt加密后的哈希值
-- 实际使用时请用真实BCrypt哈希值替换，例如:
-- 123456 的BCrypt哈希值: $2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW