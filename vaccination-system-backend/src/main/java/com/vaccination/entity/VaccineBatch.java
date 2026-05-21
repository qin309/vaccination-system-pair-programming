package com.vaccination.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("vaccine_batch")
public class VaccineBatch {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long vaccineId;
    private String batchNo; // 批次号
    private LocalDate productionDate; // 生产日期
    private LocalDate expiryDate; // 有效期
    private Integer quantity; // 库存数量
    private Integer usedQuantity; // 已使用数量
    private Integer status; // 0-正常 1-临期 2-过期

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
