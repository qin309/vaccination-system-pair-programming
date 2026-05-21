package com.vaccination.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vaccination.dto.LoginDTO;
import com.vaccination.dto.RegisterDTO;
import com.vaccination.entity.User;
import com.vaccination.mapper.UserMapper;
import com.vaccination.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final int MAX_LOGIN_FAIL_COUNT = 5;
    private static final int LOCK_MINUTES = 30;

    @Override
    public User login(LoginDTO dto) {
        User user = getByUsername(dto.getUsername());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (user.getStatus() == 1) {
            throw new RuntimeException("账户已被禁用");
        }
        // 检查账户是否被锁定
        if (isAccountLocked(user)) {
            throw new RuntimeException("账户已被锁定，请" + LOCK_MINUTES + "分钟后再试");
        }
        if (!BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
            // 密码错误，增加失败次数
            handleLoginFail(user);
            int remaining = MAX_LOGIN_FAIL_COUNT - (user.getLoginFailCount() == null ? 0 : user.getLoginFailCount());
            throw new RuntimeException("密码错误，剩余尝试次数：" + remaining + "次");
        }
        // 登录成功，重置失败次数
        resetLoginFailCount(user);
        return user;
    }

    /**
     * 检查账户是否被锁定
     */
    private boolean isAccountLocked(User user) {
        if (user.getLoginFailCount() != null && user.getLoginFailCount() >= MAX_LOGIN_FAIL_COUNT) {
            if (user.getLockTime() != null && user.getLockTime().plusMinutes(LOCK_MINUTES).isAfter(LocalDateTime.now())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 处理登录失败
     */
    private void handleLoginFail(User user) {
        int failCount = user.getLoginFailCount() == null ? 0 : user.getLoginFailCount();
        failCount++;
        user.setLoginFailCount(failCount);
        if (failCount >= MAX_LOGIN_FAIL_COUNT) {
            user.setLockTime(LocalDateTime.now());
        }
        updateById(user);
    }

    /**
     * 重置登录失败次数
     */
    private void resetLoginFailCount(User user) {
        if (user.getLoginFailCount() != null && user.getLoginFailCount() > 0) {
            user.setLoginFailCount(0);
            user.setLockTime(null);
            updateById(user);
        }
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