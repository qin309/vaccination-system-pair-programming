package com.vaccination.controller;

import com.vaccination.common.Result;
import com.vaccination.entity.FamilyMember;
import com.vaccination.service.FamilyMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/family")
public class FamilyMemberController {

    @Autowired
    private FamilyMemberService familyMemberService;

    @GetMapping("/list")
    public Result<List<FamilyMember>> list(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(familyMemberService.findByUserId(userId));
    }

    @PostMapping("/add")
    public Result<Boolean> add(HttpServletRequest request, @RequestBody FamilyMember member) {
        Long userId = (Long) request.getAttribute("userId");
        member.setUserId(userId);
        return Result.success(familyMemberService.addFamilyMember(member));
    }

    @PutMapping("/update")
    public Result<Boolean> update(HttpServletRequest request, @RequestBody FamilyMember member) {
        Long userId = (Long) request.getAttribute("userId");
        member.setUserId(userId);
        return Result.success(familyMemberService.updateFamilyMember(member));
    }

    @DeleteMapping("/delete/{id}")
    public Result<Boolean> delete(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(familyMemberService.deleteFamilyMember(id, userId));
    }

    @GetMapping("/detail/{id}")
    public Result<FamilyMember> detail(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(familyMemberService.getByIdAndUserId(id, userId));
    }
}
