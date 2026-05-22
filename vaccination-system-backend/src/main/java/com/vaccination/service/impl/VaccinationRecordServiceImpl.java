package com.vaccination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vaccination.dto.VaccinationDTO;
import com.vaccination.entity.Appointment;
import com.vaccination.entity.VaccinationRecord;
import com.vaccination.mapper.VaccinationRecordMapper;
import com.vaccination.service.AppointmentService;
import com.vaccination.service.VaccinationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VaccinationRecordServiceImpl extends ServiceImpl<VaccinationRecordMapper, VaccinationRecord> implements VaccinationRecordService {

    @Autowired
    private AppointmentService appointmentService;

    @Override
    public List<VaccinationRecord> findByUserId(Long userId) {
        return baseMapper.findByUserId(userId);
    }

    @Override
    public List<VaccinationRecord> findByFamilyMemberId(Long familyMemberId) {
        return baseMapper.findByFamilyMemberId(familyMemberId);
    }

    @Override
    @Transactional
    public VaccinationRecord createRecord(VaccinationDTO dto) {
        VaccinationRecord record = new VaccinationRecord();
        record.setAppointmentId(dto.getAppointmentId());
        record.setUserId(dto.getUserId());
        record.setFamilyMemberId(dto.getFamilyMemberId());
        record.setVaccineId(dto.getVaccineId());
        record.setVaccineBatchId(dto.getVaccineBatchId());
        record.setInjectionSite(dto.getInjectionSite());
        record.setDoctorId(dto.getDoctorId());
        record.setDoctorName(dto.getDoctorName());
        record.setVaccinationTime(dto.getVaccinationTime());
        record.setStatus(0);
        record.setNextVaccinationDate(dto.getNextVaccinationDate());
        save(record);

        if (dto.getAppointmentId() != null) {
            appointmentService.updateStatus(dto.getAppointmentId(), 1);
        }
        return record;
    }

    @Override
    public boolean updateAdverseReaction(Long recordId, String reaction) {
        VaccinationRecord record = getById(recordId);
        record.setStatus(1);
        record.setAdverseReaction(reaction);
        return updateById(record);
    }
}
