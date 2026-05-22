package com.vaccination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vaccination.entity.AppointmentSlot;
import com.vaccination.mapper.AppointmentSlotMapper;
import com.vaccination.service.AppointmentSlotService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentSlotServiceImpl extends ServiceImpl<AppointmentSlotMapper, AppointmentSlot> implements AppointmentSlotService {

    @Override
    public List<AppointmentSlot> findAvailableSlots(String date) {
        return baseMapper.findAvailableSlots(date);
    }

    @Override
    public boolean incrementCount(Long slotId) {
        return baseMapper.incrementCount(slotId) > 0;
    }

    @Override
    public boolean decrementCount(Long slotId) {
        return baseMapper.decrementCount(slotId) > 0;
    }

    @Override
    public boolean createSlotsForDate(String date, int count) {
        LocalDate slotDate = LocalDate.parse(date);
        List<AppointmentSlot> slots = new ArrayList<>();

        LocalTime[] times = {
            LocalTime.of(8, 0),
            LocalTime.of(9, 0),
            LocalTime.of(10, 0),
            LocalTime.of(11, 0),
            LocalTime.of(14, 0),
            LocalTime.of(15, 0),
            LocalTime.of(16, 0)
        };

        for (LocalTime startTime : times) {
            LocalTime endTime = startTime.plusHours(1);
            for (int i = 0; i < count; i++) {
                AppointmentSlot slot = new AppointmentSlot();
                slot.setSlotDate(slotDate);
                slot.setStartTime(startTime);
                slot.setEndTime(endTime);
                slot.setMaxCount(20);
                slot.setCurrentCount(0);
                slot.setStatus(0);
                slots.add(slot);
            }
        }

        return saveBatch(slots);
    }
}