package com.vaccination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vaccination.entity.Appointment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AppointmentMapper extends BaseMapper<Appointment> {

    @Select("SELECT * FROM appointment WHERE user_id = #{userId} AND deleted = 0 ORDER BY appointment_date DESC")
    List<Appointment> findByUserId(Long userId);

    @Select("SELECT COUNT(*) FROM appointment WHERE appointment_date = #{date} AND appointment_time = #{time} AND status IN (0, 1) AND deleted = 0")
    int countByDateAndTime(@Param("date") String date, @Param("time") String time);
}
