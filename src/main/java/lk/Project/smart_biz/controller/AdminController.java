package lk.Project.smart_biz.controller;

import lk.Project.smart_biz.dto.AdminDto;
import lk.Project.smart_biz.dto.AdminLoginDto;
import lk.Project.smart_biz.dto.AdminLoginResponseDto;
import lk.Project.smart_biz.dto.AdminResponseDto;
import lk.Project.smart_biz.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin
public class AdminController {

    private final AdminService adminService;//

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/save")
    public ResponseEntity<AdminResponseDto> saveAdmin(@RequestBody AdminDto adminDto) {
        return ResponseEntity.ok(adminService.saveAdmin(adminDto));
    }

    @PostMapping("/login")
    public AdminLoginResponseDto login(@RequestBody AdminLoginDto adminLoginDto) {
        return adminService.login(adminLoginDto);
    }
}
