package com.vaccination.controller;

import com.vaccination.common.Result;
import com.vaccination.entity.AppointmentSlot;
import com.vaccination.service.AppointmentSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment-slot")
public class AppointmentSlotController {

    @Autowired
    private AppointmentSlotService appointmentSlotService;

    @GetMapping("/available")
    public Result<List<AppointmentSlot>> availableSlots(@RequestParam String date) {
        return Result.success(appointmentSlotService.findAvailableSlots(date));
    }

    @PostMapping("/create")
    public Result<Boolean> createSlots(@RequestParam String date, @RequestParam(defaultValue = "10") int count) {
        return Result.success(appointmentSlotService.createSlotsForDate(date, count));
    }

    @GetMapping("/list")
    public Result<List<AppointmentSlot>> list() {
        return Result.success(appointmentSlotService.list());
    }

    @GetMapping("/detail/{id}")
    public Result<AppointmentSlot> detail(@PathVariable Long id) {
        return Result.success(appointmentSlotService.getById(id));
    }
}