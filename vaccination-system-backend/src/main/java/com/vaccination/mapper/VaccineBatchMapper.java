package com.vaccination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vaccination.entity.VaccineBatch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VaccineBatchMapper extends BaseMapper<VaccineBatch> {

    @Select("SELECT * FROM vaccine_batch WHERE vaccine_id = #{vaccineId} AND status = 0 AND quantity > used_quantity AND deleted = 0 ORDER BY expiry_date ASC LIMIT 1")
    VaccineBatch findAvailableBatch(Long vaccineId);

    @Select("SELECT * FROM vaccine_batch WHERE expiry_date <= DATEADD(DAY, 30, CURRENT_DATE) AND status = 0 AND deleted = 0")
    List<VaccineBatch> findExpiringBatches();
}
