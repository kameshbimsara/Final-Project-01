package lk.Project.SmartBiz.service.impl;

import lk.Project.SmartBiz.dto.AdminDto;
import lk.Project.SmartBiz.dto.AdminLoginDto;
import lk.Project.SmartBiz.dto.AdminLoginResponseDto;
import lk.Project.SmartBiz.dto.AdminResponseDto;
import lk.Project.SmartBiz.entity.Admin;
import lk.Project.SmartBiz.repo.AdminRepo;
import lk.Project.SmartBiz.service.AdminService;
import lk.Project.SmartBiz.util.JwtUtil;
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
