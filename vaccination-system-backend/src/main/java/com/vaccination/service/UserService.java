package com.vaccination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vaccination.entity.User;
import com.vaccination.dto.LoginDTO;
import com.vaccination.dto.RegisterDTO;

public interface UserService extends IService<User> {

    User login(LoginDTO dto);

    User register(RegisterDTO dto);

    User getByUsername(String username);

    boolean updatePassword(Long userId, String oldPassword, String newPassword);
}