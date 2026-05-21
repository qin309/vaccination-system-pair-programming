package com.vaccination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vaccination.entity.Vaccine;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VaccineMapper extends BaseMapper<Vaccine> {
}
