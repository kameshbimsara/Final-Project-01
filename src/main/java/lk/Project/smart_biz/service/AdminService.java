package lk.Project.smart_biz.service;

import lk.Project.smart_biz.dto.AdminDto;
import lk.Project.smart_biz.dto.AdminLoginDto;
import lk.Project.smart_biz.dto.AdminLoginResponseDto;
import lk.Project.smart_biz.dto.AdminResponseDto;

public interface AdminService {
    AdminResponseDto saveAdmin(AdminDto adminDto);
    AdminLoginResponseDto login(AdminLoginDto adminLoginDto);
}
