package com.vaccination.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
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
}
