package com.vaccination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vaccination.entity.AppointmentSlot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AppointmentSlotMapper extends BaseMapper<AppointmentSlot> {

    @Select("SELECT * FROM appointment_slot WHERE slot_date = #{date} AND status = 0 AND deleted = 0 ORDER BY start_time")
    List<AppointmentSlot> findAvailableSlots(@Param("date") String date);

    @Update("UPDATE appointment_slot SET current_count = current_count + 1 WHERE id = #{slotId} AND current_count < max_count AND deleted = 0")
    int incrementCount(@Param("slotId") Long slotId);

    @Update("UPDATE appointment_slot SET current_count = current_count - 1 WHERE id = #{slotId} AND current_count > 0 AND deleted = 0")
    int decrementCount(@Param("slotId") Long slotId);
}