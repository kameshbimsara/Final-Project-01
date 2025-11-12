package lk.Project.SmartBiz.service.impl;

import lk.Project.SmartBiz.dto.BizSupplerDto;
import lk.Project.SmartBiz.entity.BizSuppler;
import lk.Project.SmartBiz.entity.Business;
import lk.Project.SmartBiz.repo.BizSupplerRepo;
import lk.Project.SmartBiz.repo.BusinessRepo;
import lk.Project.SmartBiz.service.BizSupplerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BizSupplerServiceImpl implements BizSupplerService {

    BizSupplerRepo bizSupplerRepo;
    BusinessRepo businessRepo;

    public BizSupplerServiceImpl(BizSupplerRepo bizSupplerRepo , BusinessRepo businessRepo) {
        this.businessRepo = businessRepo;
        this.bizSupplerRepo = bizSupplerRepo;

    }

    @Override
    public BizSupplerDto saveBizSuppler(BizSupplerDto bizSupplerDto) {
        Business business = businessRepo.findById(bizSupplerDto.getBusiness_id())
                .orElseThrow(() -> new RuntimeException("Business not found"));
        BizSuppler bizSuppler = new BizSuppler(null , bizSupplerDto.getCompanyName(), bizSupplerDto.getContactNo(),business);
        BizSuppler save = bizSupplerRepo.save(bizSuppler);
        return new BizSupplerDto(save.getId(), save.getCompanyName(), save.getContactNo(), save.getBusiness().getId());
    }

    @Override
    public BizSupplerDto updateBizSuppler(BizSupplerDto bizSupplerDto) {
        Optional<BizSuppler> byId = bizSupplerRepo.findById(bizSupplerDto.getId());
        if (byId.isPresent()) {
            BizSuppler bizSuppler = byId.get();
            bizSuppler.setCompanyName(bizSupplerDto.getCompanyName());
            bizSuppler.setContactNo(bizSupplerDto.getContactNo());
            Business business = businessRepo.findById(bizSupplerDto.getBusiness_id())
                    .orElseThrow(() -> new RuntimeException("Business not found"));
            bizSuppler.setBusiness(business);
            BizSuppler update = bizSupplerRepo.save(bizSuppler);
            return new BizSupplerDto(update.getId(), update.getCompanyName(), update.getContactNo(), update.getBusiness().getId());
        }
        return null;
    }

    @Override
    public BizSupplerDto deleteBizSuppler(Integer id) {
        BizSuppler bizSuppler = bizSupplerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("BizOwner not found"));
        bizSupplerRepo.deleteById(bizSuppler.getId());
        return new BizSupplerDto(bizSuppler.getId(),bizSuppler.getCompanyName(), bizSuppler.getContactNo(), bizSuppler.getBusiness().getId());
    }

    @Override
    public BizSupplerDto getBizSupplerById(Integer id) {
        BizSuppler bizSuppler = bizSupplerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("BizSuppler not found"));
        return new BizSupplerDto(bizSuppler.getId(), bizSuppler.getCompanyName(), bizSuppler.getContactNo(), bizSuppler.getBusiness().getId());
    }

    @Override
    public List<BizSupplerDto> getAllBizSuppler() {
        List<BizSuppler> bizSupplers = bizSupplerRepo.findAll();
        return bizSupplers.stream()
                .map(suppler -> new BizSupplerDto(
                        suppler.getId(),
                        suppler.getCompanyName(),
                        suppler.getContactNo(),
                        suppler.getBusiness().getId()
                ))
                .toList();
    }
}
