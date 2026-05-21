package com.vaccination.controller;

import com.vaccination.common.Result;
import com.vaccination.dto.LoginDTO;
import com.vaccination.dto.RegisterDTO;
import com.vaccination.entity.User;
import com.vaccination.service.UserService;
import com.vaccination.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody LoginDTO dto) {
        User user = userService.login(dto);
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);
        return Result.success(data);
    }

    @PostMapping("/register")
    public Result<User> register(@RequestBody RegisterDTO dto) {
        User user = userService.register(dto);
        return Result.success(user);
    }

    @GetMapping("/info")
    public Result<User> getInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        User user = userService.getById(userId);
        user.setPassword(null);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody User user, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        user.setId(userId);
        return Result.success(userService.updateById(user));
    }

    @PostMapping("/changePassword")
    public Result<Boolean> changePassword(HttpServletRequest request,
                                          @RequestParam String oldPassword,
                                          @RequestParam String newPassword) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(userService.updatePassword(userId, oldPassword, newPassword));
    }

    @GetMapping("/list")
    public Result<?> list(HttpServletRequest request) {
        Integer role = (Integer) request.getAttribute("role");
        if (role != 2) {
            return Result.error(403, "无权限访问");
        }
        return Result.success(userService.list());
    }
}