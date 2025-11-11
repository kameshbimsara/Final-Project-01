package lk.Project.SmartBiz.service.impl;

import jakarta.transaction.Transactional;
import lk.Project.SmartBiz.dto.BizOwnerDto;
import lk.Project.SmartBiz.dto.BizOwnerDtoReturn;
import lk.Project.SmartBiz.entity.BizOwner;
import lk.Project.SmartBiz.entity.Business;
import lk.Project.SmartBiz.repo.BizOwnerRepo;
import lk.Project.SmartBiz.repo.BusinessRepo;
import lk.Project.SmartBiz.service.BizOwnerService;
import lk.Project.SmartBiz.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BizOwnerServiceImpl implements BizOwnerService {

    private final BizOwnerRepo bizOwnerRepo;
    private final BusinessRepo businessRepo;
    private final JwtUtil jwtUtil;

    public BizOwnerServiceImpl(BizOwnerRepo bizOwnerRepo, BusinessRepo businessRepo, JwtUtil jwtUtil) {
        this.bizOwnerRepo = bizOwnerRepo;
        this.businessRepo = businessRepo;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public BizOwnerDto saveBizOwner(BizOwnerDto bizOwnerDto) {
        Business business = businessRepo.findById(bizOwnerDto.getBusiness_id())
                .orElseThrow(() -> new RuntimeException("Business not found"));

        BizOwner bizOwner = new BizOwner(null, bizOwnerDto.getName(), bizOwnerDto.getUsername(), bizOwnerDto.getPassword(), business);
        BizOwner save = bizOwnerRepo.save(bizOwner);

        // generate token dynamically
        String token = jwtUtil.generateToken(bizOwner.getUsername(), "BIZ_OWNER");

        return new BizOwnerDto(save.getId(), save.getName(), save.getUsername(), save.getPassword(), save.getBusiness().getId(), token);
    }

    @Override
    public BizOwnerDtoReturn updateBizOwner(BizOwnerDto bizOwnerDto) {
        BizOwner bizOwner = bizOwnerRepo.findById(bizOwnerDto.getId())
                .orElseThrow(() -> new RuntimeException("BizOwner not found"));

        bizOwner.setName(bizOwnerDto.getName());
        bizOwner.setUsername(bizOwnerDto.getUsername());
        bizOwner.setPassword(bizOwnerDto.getPassword());

        Business business = businessRepo.findById(bizOwnerDto.getBusiness_id())
                .orElseThrow(() -> new RuntimeException("Business not found"));

        bizOwner.setBusiness(business);
        BizOwner updated = bizOwnerRepo.save(bizOwner);

        String token = jwtUtil.generateToken(updated.getUsername(), "BIZ_OWNER");

        return new BizOwnerDtoReturn(updated.getId(), updated.getName(), updated.getUsername(), token);
    }


    @Transactional
    @Override
    public BizOwnerDto deleteBizOwner(Integer id) {
        BizOwner bizOwner = bizOwnerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("BizOwner not found"));

        bizOwnerRepo.deleteById(id);

        String token = jwtUtil.generateToken(bizOwner.getUsername(), "BIZ_OWNER");

        return new BizOwnerDto(bizOwner.getId(), bizOwner.getName(), bizOwner.getUsername(), bizOwner.getPassword(), bizOwner.getBusiness().getId(), token);
    }

    @Override
    public BizOwnerDto getBizOwnerById(Integer id) {
        BizOwner bizOwner = bizOwnerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("BizOwner not found"));

        String token = jwtUtil.generateToken(bizOwner.getUsername(), "BIZ_OWNER");

        return new BizOwnerDto(bizOwner.getId(), bizOwner.getName(), bizOwner.getUsername(), bizOwner.getPassword(), bizOwner.getBusiness().getId(), token);
    }

    @Override
    public List<BizOwnerDtoReturn> getAllBizOwners() {
        return bizOwnerRepo.findAll().stream()
                .map(owner -> new BizOwnerDtoReturn(
                        owner.getId(),
                        owner.getName(),
                        owner.getUsername(),
                        jwtUtil.generateToken(owner.getUsername(), "BIZ_OWNER")
                ))
                .collect(Collectors.toList());
    }


}
