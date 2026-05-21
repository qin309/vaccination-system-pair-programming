package com.vaccination.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vaccination.dto.LoginDTO;
import com.vaccination.dto.RegisterDTO;
import com.vaccination.entity.User;
import com.vaccination.mapper.UserMapper;
import com.vaccination.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User login(LoginDTO dto) {
        User user = getByUsername(dto.getUsername());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        if (user.getStatus() == 1) {
            throw new RuntimeException("账户已被禁用");
        }
        return user;
    }

    @Override
    public User register(RegisterDTO dto) {
        User existUser = getByUsername(dto.getUsername());
        if (existUser != null) {
            throw new RuntimeException("用户名已存在");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
        user.setPhone(dto.getPhone());
        user.setRealName(dto.getRealName());
        user.setIdCard(dto.getIdCard());
        user.setRole(0);
        user.setStatus(0);
        save(user);
        return user;
    }

    @Override
    public User getByUsername(String username) {
        return baseMapper.selectOne(new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<User>()
            .eq("username", username));
    }

    @Override
    public boolean updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = getById(userId);
        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            throw new RuntimeException("原密码错误");
        }
        user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        return updateById(user);
    }
}