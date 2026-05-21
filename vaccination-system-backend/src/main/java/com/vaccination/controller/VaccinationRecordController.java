package com.vaccination.controller;

import com.vaccination.common.Result;
import com.vaccination.dto.VaccinationDTO;
import com.vaccination.entity.VaccinationRecord;
import com.vaccination.service.VaccinationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/record")
public class VaccinationRecordController {

    @Autowired
    private VaccinationRecordService vaccinationRecordService;

    @GetMapping("/my")
    public Result<List<VaccinationRecord>> myRecords(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(vaccinationRecordService.findByUserId(userId));
    }

    @GetMapping("/family/{familyMemberId}")
    public Result<List<VaccinationRecord>> familyRecords(HttpServletRequest request,
                                                          @PathVariable Long familyMemberId) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(vaccinationRecordService.findByFamilyMemberId(familyMemberId));
    }

    @PostMapping("/create")
    public Result<VaccinationRecord> create(HttpServletRequest request, @RequestBody VaccinationDTO dto) {
        Integer role = (Integer) request.getAttribute("role");
        if (role != 1 && role != 2) {
            return Result.error(403, "无权限操作");
        }
        return Result.success(vaccinationRecordService.createRecord(dto));
    }

    @PostMapping("/adverse/{id}")
    public Result<Boolean> recordAdverse(HttpServletRequest request,
                                          @PathVariable Long id,
                                          @RequestParam String reaction) {
        Integer role = (Integer) request.getAttribute("role");
        if (role != 1 && role != 2) {
            return Result.error(403, "无权限操作");
        }
        return Result.success(vaccinationRecordService.updateAdverseReaction(id, reaction));
    }

    @GetMapping("/list")
    public Result<List<VaccinationRecord>> list(HttpServletRequest request) {
        Integer role = (Integer) request.getAttribute("role");
        if (role != 1 && role != 2) {
            return Result.error(403, "无权限访问");
        }
        return Result.success(vaccinationRecordService.list());
    }
}
