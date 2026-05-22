package com.vaccination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vaccination.dto.AppointmentDTO;
import com.vaccination.entity.Appointment;
import com.vaccination.entity.AppointmentSlot;
import com.vaccination.entity.VaccineBatch;
import com.vaccination.mapper.AppointmentMapper;
import com.vaccination.mapper.AppointmentSlotMapper;
import com.vaccination.service.AppointmentService;
import com.vaccination.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {

    @Autowired
    private AppointmentSlotMapper appointmentSlotMapper;

    @Autowired
    private VaccineService vaccineService;

    @Override
    public List<Appointment> findByUserId(Long userId) {
        return baseMapper.findByUserId(userId);
    }

    @Override
    @Transactional
    public Appointment createAppointment(AppointmentDTO dto) {
        VaccineBatch batch = vaccineService.findAvailableBatch(dto.getVaccineId());
        if (batch == null) {
            throw new RuntimeException("该疫苗暂无库存");
        }

        Appointment appointment = new Appointment();
        appointment.setUserId(dto.getUserId());
        appointment.setFamilyMemberId(dto.getFamilyMemberId());
        appointment.setVaccineId(dto.getVaccineId());
        appointment.setVaccineBatchId(batch.getId());
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setStatus(0);
        appointment.setRemark(dto.getRemark());
        save(appointment);

        vaccineService.updateBatchStock(batch.getId(), 1);
        return appointment;
    }

    @Override
    @Transactional
    public boolean cancelAppointment(Long appointmentId, Long userId) {
        Appointment appointment = getById(appointmentId);
        if (appointment == null || !appointment.getUserId().equals(userId)) {
            throw new RuntimeException("预约不存在或无权操作");
        }
        if (appointment.getStatus() != 0) {
            throw new RuntimeException("该预约无法取消");
        }
        appointment.setStatus(2);
        updateById(appointment);

        vaccineService.updateBatchStock(appointment.getVaccineBatchId(), -1);
        return true;
    }

    @Override
    public boolean updateStatus(Long appointmentId, Integer status) {
        Appointment appointment = getById(appointmentId);
        appointment.setStatus(status);
        return updateById(appointment);
    }
}
