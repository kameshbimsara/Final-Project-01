package lk.Project.smart_biz.service.impl;

import lk.Project.smart_biz.dto.AdminDto;
import lk.Project.smart_biz.dto.AdminLoginDto;
import lk.Project.smart_biz.dto.AdminLoginResponseDto;
import lk.Project.smart_biz.dto.AdminResponseDto;
import lk.Project.smart_biz.entity.Admin;
import lk.Project.smart_biz.repo.AdminRepo;
import lk.Project.smart_biz.service.AdminService;
import lk.Project.smart_biz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepo adminRepo;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(AdminRepo adminRepo, JwtUtil jwtUtil) {
        this.adminRepo = adminRepo;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public AdminResponseDto saveAdmin(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setUsername(adminDto.getUsername());
        admin.setEmail(adminDto.getEmail());
        admin.setPassword(passwordEncoder.encode(adminDto.getPassword()));
        Admin saved = adminRepo.save(admin);
        return new AdminResponseDto(saved.getUsername(),saved.getEmail());
    }

    @Override
    public AdminLoginResponseDto login(AdminLoginDto loginDto) {
        Admin admin = adminRepo.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(loginDto.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(admin.getUsername(), "ADMIN");
        return new AdminLoginResponseDto(token);
    }
}
