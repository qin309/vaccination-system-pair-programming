package com.vaccination.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("family_member")
public class FamilyMember {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId; // 所属用户
    private String name;
    private String idCard;
    private String phone;
    private LocalDate birthDate;
    private Integer gender; // 0-男 1-女
    private String relation; // 与用户关系：本人/子女/配偶等
    private Integer status; // 0-正常 1-禁用

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
