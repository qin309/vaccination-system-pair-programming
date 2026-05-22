package com.vaccination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vaccination.entity.FamilyMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FamilyMemberMapper extends BaseMapper<FamilyMember> {

    @Select("SELECT * FROM family_member WHERE user_id = #{userId} AND deleted = 0")
    List<FamilyMember> findByUserId(Long userId);
}
