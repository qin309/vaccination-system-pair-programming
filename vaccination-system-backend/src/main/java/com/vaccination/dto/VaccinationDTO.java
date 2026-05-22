package com.vaccination.dto;

import java.time.LocalDateTime;

public class VaccinationDTO {
    private Long appointmentId;
    private Long userId;
    private Long familyMemberId;
    private Long vaccineId;
    private Long vaccineBatchId;
    private String injectionSite;
    private Long doctorId;
    private String doctorName;
    private LocalDateTime vaccinationTime;
    private String nextVaccinationDate;

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

    public String getInjectionSite() { return injectionSite; }
    public void setInjectionSite(String injectionSite) { this.injectionSite = injectionSite; }

    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public LocalDateTime getVaccinationTime() { return vaccinationTime; }
    public void setVaccinationTime(LocalDateTime vaccinationTime) { this.vaccinationTime = vaccinationTime; }

    public String getNextVaccinationDate() { return nextVaccinationDate; }
    public void setNextVaccinationDate(String nextVaccinationDate) { this.nextVaccinationDate = nextVaccinationDate; }
}