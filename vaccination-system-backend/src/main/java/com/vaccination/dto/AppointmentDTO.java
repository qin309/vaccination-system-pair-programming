package com.vaccination.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentDTO {
    private Long userId;
    private Long familyMemberId;
    private Long vaccineId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String remark;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getFamilyMemberId() { return familyMemberId; }
    public void setFamilyMemberId(Long familyMemberId) { this.familyMemberId = familyMemberId; }

    public Long getVaccineId() { return vaccineId; }
    public void setVaccineId(Long vaccineId) { this.vaccineId = vaccineId; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}