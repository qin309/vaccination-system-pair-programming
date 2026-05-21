package com.vaccination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vaccination.entity.VaccinationRecord;
import com.vaccination.dto.VaccinationDTO;

import java.util.List;

public interface VaccinationRecordService extends IService<VaccinationRecord> {

    List<VaccinationRecord> findByUserId(Long userId);

    List<VaccinationRecord> findByFamilyMemberId(Long familyMemberId);

    VaccinationRecord createRecord(VaccinationDTO dto);

    boolean updateAdverseReaction(Long recordId, String reaction);
}
