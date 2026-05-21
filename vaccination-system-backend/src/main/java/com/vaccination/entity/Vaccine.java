package com.vaccination.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("vaccine")
public class Vaccine {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;
    private String code; // 疫苗编码
    private String manufacturer; // 生产厂家
    private Integer type; // 0-免费 1-自费
    private BigDecimal price;
    private String description;
    private Integer status; // 0-正常 1-禁用

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
