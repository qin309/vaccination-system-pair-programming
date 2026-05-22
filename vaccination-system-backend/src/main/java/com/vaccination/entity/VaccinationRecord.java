package com.vaccination.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;

@TableName("vaccination_record")
public class VaccinationRecord {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long appointmentId;
    private Long userId;
    private Long familyMemberId;
    private Long vaccineId;
    private Long vaccineBatchId;
    private String batchNo;
    private String injectionSite;
    private Long doctorId;
    private String doctorName;
    private LocalDateTime vaccinationTime;
    private Integer status;
    private String adverseReaction;
    private String nextVaccinationDate;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getFamilyMemberId() { return familyMemberId; }
    public void setFamilyMemberId(Long familyMemberId) { this.familyMemberId = familyMemberId; }

    public Long getVaccineId() { return vaccineId; }
    public void setVaccineId(Long vaccineId) { this.vaccineId = vaccineId; }

    public Long getVaccineBatchId() { return vaccineBatchId; }
    public void setVaccineBatchId(Long vaccineBatchId) { this.vaccineBatchId = vaccineBatchId; }

    public String getBatchNo() { return batchNo; }
    public void setBatchNo(String batchNo) { this.batchNo = batchNo; }

    public String getInjectionSite() { return injectionSite; }
    public void setInjectionSite(String injectionSite) { this.injectionSite = injectionSite; }

    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public LocalDateTime getVaccinationTime() { return vaccinationTime; }
    public void setVaccinationTime(LocalDateTime vaccinationTime) { this.vaccinationTime = vaccinationTime; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public String getAdverseReaction() { return adverseReaction; }
    public void setAdverseReaction(String adverseReaction) { this.adverseReaction = adverseReaction; }

    public String getNextVaccinationDate() { return nextVaccinationDate; }
    public void setNextVaccinationDate(String nextVaccinationDate) { this.nextVaccinationDate = nextVaccinationDate; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }

    public Integer getDeleted() { return deleted; }
    public void setDeleted(Integer deleted) { this.deleted = deleted; }
}