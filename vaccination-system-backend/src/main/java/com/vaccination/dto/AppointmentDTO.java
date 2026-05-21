package com.vaccination.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentDTO {
    private Long userId;
    private Long familyMemberId;
    private Long vaccineId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String remark;
}
