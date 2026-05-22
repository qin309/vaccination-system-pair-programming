package com.vaccination.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("vaccination_record")
public class VaccinationRecord {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long appointmentId;
    private Long userId;
    private Long familyMemberId;
    private Long vaccineId;
    private Long vaccineBatchId;
    private String batchNo; // 接种批次号
    private String injectionSite; // 接种部位
    private Long doctorId; // 接种医生
    private String doctorName;
    private LocalDateTime vaccinationTime; // 接种时间
    private Integer status; // 0-正常 1-有不良反应
    private String adverseReaction; // 不良反应描述
    private String nextVaccinationDate; // 下次接种日期建议

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
