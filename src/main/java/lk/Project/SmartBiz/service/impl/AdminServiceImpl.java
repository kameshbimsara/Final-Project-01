package lk.Project.SmartBiz.service.impl;

import lk.Project.SmartBiz.dto.AdminDto;
import lk.Project.SmartBiz.entity.Admin;
import lk.Project.SmartBiz.repo.AdminRepo;
import lk.Project.SmartBiz.service.AdminService;
import lk.Project.SmartBiz.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepo adminRepo;
    private final JwtUtil jwtUtil;

    public AdminServiceImpl(AdminRepo adminRepo, JwtUtil jwtUtil) {
        this.adminRepo = adminRepo;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AdminDto saveAdmin(AdminDto dto) {
        // save admin to DB
        Admin admin = new Admin(null, dto.getUsername(), dto.getEmail(), dto.getPassword());
        Admin saved = adminRepo.save(admin);

        // generate JWT dynamically
        String token = jwtUtil.generateToken(saved.getEmail());

        return new AdminDto(saved.getId(), saved.getUsername(), saved.getEmail(), saved.getPassword(), token);
    }

    @Override
    public AdminDto updateAdmin(AdminDto dto) {
        Admin existing = adminRepo.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        existing.setUsername(dto.getUsername());
        existing.setEmail(dto.getEmail());
        existing.setPassword(dto.getPassword());
        Admin updated = adminRepo.save(existing);

        String token = jwtUtil.generateToken(updated.getEmail());

        return new AdminDto(updated.getId(), updated.getUsername(), updated.getEmail(), updated.getPassword(), token);
    }

    @Override
    public void deleteAdmin(Integer id) {
        adminRepo.deleteById(id);
    }

    @Override
    public AdminDto getAdminById(Integer id) {
        Admin admin = adminRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        String token = jwtUtil.generateToken(admin.getEmail());

        return new AdminDto(admin.getId(), admin.getUsername(), admin.getEmail(), admin.getPassword(), token);
    }

    @Override
    public List<AdminDto> getAllAdmins() {
        return adminRepo.findAll().stream()
                .map(a -> new AdminDto(a.getId(), a.getUsername(), a.getEmail(), a.getPassword(),
                        jwtUtil.generateToken(a.getEmail())))
                .toList();
    }
}
