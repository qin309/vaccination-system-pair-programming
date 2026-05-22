package com.vaccination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vaccination.entity.FamilyMember;
import com.vaccination.mapper.FamilyMemberMapper;
import com.vaccination.service.FamilyMemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyMemberServiceImpl extends ServiceImpl<FamilyMemberMapper, FamilyMember> implements FamilyMemberService {

    @Override
    public List<FamilyMember> findByUserId(Long userId) {
        return baseMapper.findByUserId(userId);
    }

    @Override
    public FamilyMember getByIdAndUserId(Long id, Long userId) {
        return lambdaQuery()
                .eq(FamilyMember::getId, id)
                .eq(FamilyMember::getUserId, userId)
                .one();
    }

    @Override
    public boolean addFamilyMember(FamilyMember member) {
        member.setStatus(0);
        return save(member);
    }

    @Override
    public boolean updateFamilyMember(FamilyMember member) {
        return updateById(member);
    }

    @Override
    public boolean deleteFamilyMember(Long id, Long userId) {
        FamilyMember member = getByIdAndUserId(id, userId);
        if (member == null) {
            throw new RuntimeException("家庭成员不存在或无权操作");
        }
        return removeById(id);
    }
}
