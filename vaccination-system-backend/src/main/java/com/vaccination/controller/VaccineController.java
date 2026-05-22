package com.vaccination.controller;

import com.vaccination.common.Result;
import com.vaccination.entity.Vaccine;
import com.vaccination.entity.VaccineBatch;
import com.vaccination.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/vaccine")
public class VaccineController {

    @Autowired
    private VaccineService vaccineService;

    @GetMapping("/list")
    public Result<List<Vaccine>> list() {
        return Result.success(vaccineService.findAvailableVaccines());
    }

    @GetMapping("/detail/{id}")
    public Result<Vaccine> detail(@PathVariable Long id) {
        return Result.success(vaccineService.getById(id));
    }

    @PostMapping("/add")
    public Result<Boolean> add(HttpServletRequest request, @RequestBody Vaccine vaccine) {
        Integer role = (Integer) request.getAttribute("role");
        if (role != 2) {
            return Result.error(403, "无权限操作");
        }
        return Result.success(vaccineService.save(vaccine));
    }

    @PutMapping("/update")
    public Result<Boolean> update(HttpServletRequest request, @RequestBody Vaccine vaccine) {
        Integer role = (Integer) request.getAttribute("role");
        if (role != 2) {
            return Result.error(403, "无权限操作");
        }
        return Result.success(vaccineService.updateById(vaccine));
    }

    @GetMapping("/batch/list")
    public Result<List<VaccineBatch>> batchList() {
        return Result.success(vaccineService.findAllBatches());
    }

    @GetMapping("/batch/expiring")
    public Result<List<VaccineBatch>> expiringBatches(HttpServletRequest request) {
        Integer role = (Integer) request.getAttribute("role");
        if (role != 1 && role != 2) {
            return Result.error(403, "无权限访问");
        }
        return Result.success(vaccineService.findExpiringBatches());
    }

    @PostMapping("/batch/add")
    public Result<Boolean> addBatch(HttpServletRequest request, @RequestBody VaccineBatch batch) {
        Integer role = (Integer) request.getAttribute("role");
        if (role != 2) {
            return Result.error(403, "无权限操作");
        }
        return Result.success(vaccineService.addBatch(batch));
    }
}
