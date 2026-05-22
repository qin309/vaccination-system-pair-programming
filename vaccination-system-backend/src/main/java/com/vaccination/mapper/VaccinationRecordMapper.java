package com.vaccination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vaccination.entity.VaccinationRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VaccinationRecordMapper extends BaseMapper<VaccinationRecord> {

    @Select("SELECT * FROM vaccination_record WHERE user_id = #{userId} AND deleted = 0 ORDER BY vaccination_time DESC")
    List<VaccinationRecord> findByUserId(Long userId);

    @Select("SELECT * FROM vaccination_record WHERE family_member_id = #{familyMemberId} AND deleted = 0 ORDER BY vaccination_time DESC")
    List<VaccinationRecord> findByFamilyMemberId(Long familyMemberId);
}
