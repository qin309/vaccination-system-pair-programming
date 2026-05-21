package com.vaccination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vaccination.entity.User;
import com.vaccination.dto.LoginDTO;
import com.vaccination.dto.RegisterDTO;

public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param dto 登录参数（包含用户名和密码）
     * @return 登录成功的用户信息
     * @throws RuntimeException 用户不存在/密码错误/账户被锁定
     */
    User login(LoginDTO dto);

    /**
     * 用户注册
     * @param dto 注册参数（包含用户名、密码、手机号等）
     * @return 注册成功的用户信息
     * @throws RuntimeException 用户名已存在
     */
    User register(RegisterDTO dto);

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息，不存在返回null
     */
    User getByUsername(String username);

    /**
     * 修改用户密码
     * @param userId 用户ID
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return 是否修改成功
     * @throws RuntimeException 原密码错误
     */
    boolean updatePassword(Long userId, String oldPassword, String newPassword);
}