package com.vaccination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vaccination.entity.FamilyMember;

import java.util.List;

public interface FamilyMemberService extends IService<FamilyMember> {

    List<FamilyMember> findByUserId(Long userId);

    FamilyMember getByIdAndUserId(Long id, Long userId);

    boolean addFamilyMember(FamilyMember member);

    boolean updateFamilyMember(FamilyMember member);

    boolean deleteFamilyMember(Long id, Long userId);
}
