package com.vaccination.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@TableName("appointment")
public class Appointment {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;
    private Long familyMemberId; // 家庭成员ID（如果是给孩子预约）
    private Long vaccineId;
    private Long vaccineBatchId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private Integer status; // 0-待接种 1-已接种 2-已取消 3-过期未接种
    private String remark;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
