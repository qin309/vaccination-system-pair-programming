package com.vaccination.controller;

import com.vaccination.common.Result;
import com.vaccination.dto.AppointmentDTO;
import com.vaccination.entity.Appointment;
import com.vaccination.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/my")
    public Result<List<Appointment>> myAppointments(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(appointmentService.findByUserId(userId));
    }

    @PostMapping("/create")
    public Result<Appointment> create(HttpServletRequest request, @RequestBody AppointmentDTO dto) {
        Long userId = (Long) request.getAttribute("userId");
        dto.setUserId(userId);
        return Result.success(appointmentService.createAppointment(dto));
    }

    @PostMapping("/cancel/{id}")
    public Result<Boolean> cancel(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(appointmentService.cancelAppointment(id, userId));
    }

    @GetMapping("/list")
    public Result<List<Appointment>> list(HttpServletRequest request) {
        Integer role = (Integer) request.getAttribute("role");
        if (role != 1 && role != 2) {
            return Result.error(403, "无权限访问");
        }
        return Result.success(appointmentService.list());
    }

    @GetMapping("/detail/{id}")
    public Result<Appointment> detail(@PathVariable Long id) {
        return Result.success(appointmentService.getById(id));
    }
}
