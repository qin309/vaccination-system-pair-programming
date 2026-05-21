package com.vaccination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vaccination.entity.Vaccine;
import com.vaccination.entity.VaccineBatch;
import com.vaccination.mapper.VaccineMapper;
import com.vaccination.mapper.VaccineBatchMapper;
import com.vaccination.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccineServiceImpl extends ServiceImpl<VaccineMapper, Vaccine> implements VaccineService {

    @Autowired
    private VaccineBatchMapper vaccineBatchMapper;

    @Override
    public List<Vaccine> findAvailableVaccines() {
        return lambdaQuery().eq(Vaccine::getStatus, 0).list();
    }

    @Override
    public VaccineBatch findAvailableBatch(Long vaccineId) {
        return vaccineBatchMapper.findAvailableBatch(vaccineId);
    }

    @Override
    public List<VaccineBatch> findExpiringBatches() {
        return vaccineBatchMapper.findExpiringBatches();
    }

    @Override
    public List<VaccineBatch> findAllBatches() {
        return vaccineBatchMapper.selectList(null);
    }

    @Override
    public boolean addBatch(VaccineBatch batch) {
        batch.setUsedQuantity(0);
        batch.setStatus(0);
        return vaccineBatchMapper.insert(batch) > 0;
    }

    @Override
    public boolean updateBatchStock(Long batchId, Integer usedQuantity) {
        VaccineBatch batch = vaccineBatchMapper.selectById(batchId);
        batch.setUsedQuantity(batch.getUsedQuantity() + usedQuantity);
        if (batch.getUsedQuantity() >= batch.getQuantity()) {
            batch.setStatus(2);
        }
        return vaccineBatchMapper.updateById(batch) > 0;
    }
}
