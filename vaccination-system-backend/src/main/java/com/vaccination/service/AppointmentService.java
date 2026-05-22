package com.vaccination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vaccination.entity.Appointment;
import com.vaccination.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentService extends IService<Appointment> {

    List<Appointment> findByUserId(Long userId);

    Appointment createAppointment(AppointmentDTO dto);

    boolean cancelAppointment(Long appointmentId, Long userId);

    boolean updateStatus(Long appointmentId, Integer status);
}
