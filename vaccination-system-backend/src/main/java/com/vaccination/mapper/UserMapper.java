package com.vaccination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vaccination.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}