package lk.Project.SmartBiz.controller;

import lk.Project.SmartBiz.dto.AdminDto;
import lk.Project.SmartBiz.dto.AdminLoginDto;
import lk.Project.SmartBiz.dto.AdminLoginResponseDto;
import lk.Project.SmartBiz.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/save")
    public ResponseEntity<AdminDto> saveAdmin(@RequestBody AdminDto adminDto) {
        return ResponseEntity.ok(adminService.saveAdmin(adminDto));
    }

    @PostMapping("/login")
    public AdminLoginResponseDto login(@RequestBody AdminLoginDto adminLoginDto) {
        return adminService.login(adminLoginDto);
    }

}
