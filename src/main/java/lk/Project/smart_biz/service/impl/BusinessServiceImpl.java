package lk.Project.smart_biz.service.impl;

import lk.Project.smart_biz.dto.BusinessDto;
import lk.Project.smart_biz.dto.BusinessLoginDto;
import lk.Project.smart_biz.dto.BusinessLoginResponseDto;
import lk.Project.smart_biz.entity.Admin;
import lk.Project.smart_biz.entity.Business;
import lk.Project.smart_biz.repo.BusinessRepo;
import lk.Project.smart_biz.service.BusinessService;
import lk.Project.smart_biz.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepo businessRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public BusinessServiceImpl(BusinessRepo businessRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.businessRepo = businessRepo;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public BusinessDto saveBusiness(BusinessDto businessDto) {

        Business business = new Business();
        business.setName(businessDto.getName());
        business.setLocation(businessDto.getLocation());
        business.setRegDate(LocalDate.now());
        business.setOwnerName(businessDto.getOwnerName());
        business.setOwnerContact(businessDto.getOwnerContact());
        business.setUsername(businessDto.getUsername());
        business.setPassword(passwordEncoder.encode(businessDto.getPassword()));
        business.setStatus(businessDto.getStatus() != null ? businessDto.getStatus() : 1);
        business.setAdmin(new Admin(businessDto.getAdminId()));

        Business save = businessRepo.save(business);

        return new BusinessDto(save.getId(),save.getName(),save.getRegDate(), save.getStatus(),save.getLocation(),save.getOwnerContact(),save.getUsername());
    }

    @Override
    public BusinessDto updateBusiness(Integer id, BusinessDto businessDto) {
        Optional<Business> byId = businessRepo.findById(id);
        if (byId.isPresent()) {
            Business business = byId.get();
            business.setName(businessDto.getName());
            business.setLocation(businessDto.getLocation());
            business.setRegDate(LocalDate.now());
            business.setOwnerName(businessDto.getOwnerName());
            business.setOwnerContact(businessDto.getOwnerContact());
            business.setUsername(businessDto.getUsername());
            business.setPassword(passwordEncoder.encode(businessDto.getPassword()));
            business.setStatus(businessDto.getStatus());
            Business updatedBusiness = businessRepo.save(business);
            return new BusinessDto(updatedBusiness.getId(),updatedBusiness.getName(), updatedBusiness.getRegDate(),
                    updatedBusiness.getStatus(),updatedBusiness.getLocation(),updatedBusiness.getOwnerContact(),updatedBusiness.getUsername());
        }
        return null;
    }

    @Override
    public BusinessDto deleteBusiness(Integer id) {
        Optional<Business> byId = businessRepo.findById(id);
        if (byId.isPresent()) {
            businessRepo.deleteById(id);
            Business business = byId.get();
            return new BusinessDto(business.getId(),business.getName(),business.getRegDate(),business.getStatus(),business.getLocation(),business.getOwnerContact(),business.getUsername());
        }
        return null;
    }

    @Override
    public BusinessDto getBusinessById(Integer id) {
        Optional<Business> byId = businessRepo.findById(id);
        if (byId.isPresent()) {
            Business businessId = byId.get();
            return new BusinessDto(businessId.getId(),businessId.getName(),businessId.getRegDate(),businessId.getStatus(),businessId.getLocation(),businessId.getOwnerContact(),businessId.getUsername());
        }
        return null;
    }

    @Override
    public List<BusinessDto> getAllBusiness() {
        List<Business> all = businessRepo.findAll();
        return all.stream()
                .map(business -> new BusinessDto(business.getId(),business.getName(),business.getRegDate(),business.getStatus(),business.getLocation(),business.getOwnerContact(),business.getUsername()))
                .toList();
    }

    @Override
    @Transactional
    public void updateStatus(Integer id, Integer status) {
        Business business = businessRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Business not found"));

        business.setStatus(status);
    }

    @Override
    public BusinessLoginResponseDto login(BusinessLoginDto loginDto) {
        Business business = businessRepo.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(loginDto.getPassword(), business.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(business.getUsername(), "BUSINESS");

        return new BusinessLoginResponseDto(
                business.getId(),
                token,
                business.getOwnerName()
        );
    }
}
