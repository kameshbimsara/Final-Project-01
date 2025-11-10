package lk.Project.SmartBiz.service;

import lk.Project.SmartBiz.dto.AdminDto;

import java.util.List;

public interface AdminService {

    AdminDto saveAdmin(AdminDto dto);
    AdminDto updateAdmin(AdminDto dto);
    void deleteAdmin(Integer id);
    AdminDto getAdminById(Integer id);
    List<AdminDto> getAllAdmins();
}
