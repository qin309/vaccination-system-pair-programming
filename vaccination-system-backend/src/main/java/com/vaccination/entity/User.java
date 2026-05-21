package com.vaccination.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sys_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;
    private String password;
    private String phone;
    private String realName;
    private String idCard;
    private Integer role; // 0-普通用户 1-医护人员 2-管理员
    private Integer status; // 0-正常 1-禁用

    private Integer loginFailCount; // 登录失败次数

    private LocalDateTime lockTime; // 账户锁定时间

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}