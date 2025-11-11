package lk.Project.SmartBiz.service;

import lk.Project.SmartBiz.dto.AdminDto;
import lk.Project.SmartBiz.dto.AdminLoginDto;
import lk.Project.SmartBiz.dto.AdminLoginResponseDto;
import lk.Project.SmartBiz.entity.Admin;

public interface AdminService {
    AdminDto saveAdmin(AdminDto adminDto);
    AdminLoginResponseDto login(AdminLoginDto adminLoginDto);
}
