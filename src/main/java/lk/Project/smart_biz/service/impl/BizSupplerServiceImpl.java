package lk.Project.smart_biz.service.impl;

import lk.Project.smart_biz.dto.BizSupplerDto;
import lk.Project.smart_biz.entity.BizSuppler;
import lk.Project.smart_biz.entity.Business;
import lk.Project.smart_biz.repo.BatchRepo;
import lk.Project.smart_biz.repo.BizSupplerRepo;
import lk.Project.smart_biz.repo.BusinessRepo;
import lk.Project.smart_biz.service.BizSupplerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BizSupplerServiceImpl implements BizSupplerService {

    BizSupplerRepo bizSupplerRepo;
    BatchRepo batchRepo;
    BusinessRepo businessRepo;

    public BizSupplerServiceImpl(BizSupplerRepo bizSupplerRepo, BatchRepo batchRepo, BusinessRepo businessRepo) {
        this.bizSupplerRepo = bizSupplerRepo;
        this.batchRepo = batchRepo;
        this.businessRepo = businessRepo;

    }

    @Override
    public BizSupplerDto saveBizSuppler(BizSupplerDto bizSupplerDto) {

        Business business = businessRepo.findById(bizSupplerDto.getBusinessId()).orElseThrow(() -> new RuntimeException("Business not found"));

        BizSuppler bizSuppler = new BizSuppler();
        bizSuppler.setCompanyName(bizSupplerDto.getCompanyName());
        bizSuppler.setContactNo(bizSupplerDto.getContactNo());
        bizSuppler.setBusiness(business);
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

            if (bizSupplerDto.getBusinessId() != null && !bizSupplerDto.getBusinessId().equals(bizSuppler.getBusiness().getId())) {
                Business business = businessRepo.findById(bizSupplerDto.getBusinessId()).orElseThrow(() -> new RuntimeException("Business not found"));
                bizSuppler.setBusiness(business);
            }

            BizSuppler update = bizSupplerRepo.save(bizSuppler);
            return new BizSupplerDto(update.getId(), update.getCompanyName(), update.getContactNo(), update.getBusiness().getId());
        }
        return null;
    }

    @Override
    public BizSupplerDto deleteBizSuppler(Integer id) {
        BizSuppler bizSuppler = bizSupplerRepo.findById(id).orElseThrow(() -> new RuntimeException("BizOwner not found"));
        bizSupplerRepo.deleteById(bizSuppler.getId());
        return new BizSupplerDto(bizSuppler.getId(), bizSuppler.getCompanyName(), bizSuppler.getContactNo(), bizSuppler.getBusiness().getId());
    }

    @Override
    public BizSupplerDto getBizSupplerById(Integer id) {
        BizSuppler bizSuppler = bizSupplerRepo.findById(id).orElseThrow(() -> new RuntimeException("BizSuppler not found"));
        return new BizSupplerDto(bizSuppler.getId(), bizSuppler.getCompanyName(), bizSuppler.getContactNo(), bizSuppler.getBusiness().getId());
    }

    @Override
    public List<BizSupplerDto> getAllBizSuppler() {
        List<BizSuppler> bizSupplers = bizSupplerRepo.findAll();
        return bizSupplers.stream().map(suppler -> new BizSupplerDto(suppler.getId(), suppler.getCompanyName(), suppler.getContactNo(), suppler.getBusiness().getId())).toList();
    }

    @Override
    public List<BizSupplerDto> getSuppliersByBusinessId(Integer businessId) {

        List<BizSuppler> suppliers = bizSupplerRepo.findByBusiness_Id(businessId);

        return suppliers.stream()
                .map(s -> new BizSupplerDto(
                        s.getId(),
                        s.getCompanyName(),
                        s.getContactNo(),
                        s.getBusiness().getId()
                ))
                .toList();
    }



}
