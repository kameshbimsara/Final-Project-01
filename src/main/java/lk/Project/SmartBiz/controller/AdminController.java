package lk.Project.SmartBiz.controller;

import lk.Project.SmartBiz.dto.AdminDto;
import lk.Project.SmartBiz.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public AdminDto saveAdmin(@RequestBody AdminDto dto) {
        return adminService.saveAdmin(dto);
    }

    @PutMapping("/{id}")
    public AdminDto updateAdmin(@PathVariable Integer id, @RequestBody AdminDto dto) {
        dto.setId(id);
        return adminService.updateAdmin(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable Integer id) {
        adminService.deleteAdmin(id);
    }

    @GetMapping("/{id}")
    public AdminDto getAdminById(@PathVariable Integer id) {
        return adminService.getAdminById(id);
    }

    @GetMapping
    public List<AdminDto> getAllAdmins() {
        return adminService.getAllAdmins();
    }
}
