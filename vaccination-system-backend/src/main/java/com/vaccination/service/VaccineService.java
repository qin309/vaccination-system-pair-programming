package com.vaccination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vaccination.entity.Vaccine;
import com.vaccination.entity.VaccineBatch;

import java.util.List;

public interface VaccineService extends IService<Vaccine> {

    List<Vaccine> findAvailableVaccines();

    VaccineBatch findAvailableBatch(Long vaccineId);

    List<VaccineBatch> findExpiringBatches();

    List<VaccineBatch> findAllBatches();

    boolean addBatch(VaccineBatch batch);

    boolean updateBatchStock(Long batchId, Integer usedQuantity);
}
