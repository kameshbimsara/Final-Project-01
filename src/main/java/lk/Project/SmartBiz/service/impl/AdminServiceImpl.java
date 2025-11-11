package lk.Project.SmartBiz.service.impl;

import lk.Project.SmartBiz.dto.AdminDto;
import lk.Project.SmartBiz.dto.AdminLoginDto;
import lk.Project.SmartBiz.dto.AdminLoginResponseDto;
import lk.Project.SmartBiz.entity.Admin;
import lk.Project.SmartBiz.entity.Business;
import lk.Project.SmartBiz.repo.AdminRepo;
import lk.Project.SmartBiz.repo.BusinessRepo;
import lk.Project.SmartBiz.service.AdminService;
import lk.Project.SmartBiz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {



    private final JwtUtil jwtUtil;

    private final AdminRepo adminRepo;
    private final BusinessRepo businessRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(AdminRepo adminRepo, BusinessRepo businessRepo,JwtUtil jwtUtil) {
        this.adminRepo = adminRepo;
        this.businessRepo = businessRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AdminDto saveAdmin(AdminDto adminDto) {
        Optional<Business> businessOptional = businessRepo.findById(adminDto.getBusinessId());
        if (businessOptional.isEmpty()) {
            throw new RuntimeException("Business not found with ID: " + adminDto.getBusinessId());
        }

        Admin admin = new Admin();
        admin.setUsername(adminDto.getUsername());
        admin.setEmail(adminDto.getEmail());
        admin.setPassword(passwordEncoder.encode(adminDto.getPassword())); // hash password
        admin.setBusiness(businessOptional.get());

        Admin save = adminRepo.save(admin);
        return new AdminDto(save.getUsername(),save.getEmail(),save.getPassword(),save.getBusiness().getId());

    }



    @Override
    public AdminLoginResponseDto login(AdminLoginDto adminLoginDto) {
        Admin admin = adminRepo.findByUsername(adminLoginDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(adminLoginDto.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(admin.getUsername(), "ADMIN");
        return new AdminLoginResponseDto(admin.getUsername(), token);
    }

}
