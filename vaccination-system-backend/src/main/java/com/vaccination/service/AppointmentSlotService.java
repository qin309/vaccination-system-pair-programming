package com.vaccination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vaccination.entity.AppointmentSlot;

import java.util.List;

public interface AppointmentSlotService extends IService<AppointmentSlot> {

    List<AppointmentSlot> findAvailableSlots(String date);

    boolean incrementCount(Long slotId);

    boolean decrementCount(Long slotId);

    boolean createSlotsForDate(String date, int count);
}
