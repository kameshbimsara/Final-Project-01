package lk.Project.SmartBiz.service.impl;

import lk.Project.SmartBiz.dto.BusinessDto;
import lk.Project.SmartBiz.entity.BizOwner;
import lk.Project.SmartBiz.entity.Business;
import lk.Project.SmartBiz.repo.BizOwnerRepo;
import lk.Project.SmartBiz.repo.BusinessRepo;
import lk.Project.SmartBiz.service.BusinessService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessServiceImpl implements BusinessService {

    private final BizOwnerRepo bizOwnerRepo;
    BusinessRepo businessRepo;

    public BusinessServiceImpl(BusinessRepo businessRepo, BizOwnerRepo bizOwnerRepo) {
        this.businessRepo = businessRepo;
        this.bizOwnerRepo = bizOwnerRepo;
    }

    @Override
    public BusinessDto saveBusiness(BusinessDto businessDto) {
        BizOwner owner = bizOwnerRepo.findById(businessDto.getOwner_id())
        .orElseThrow(() -> new RuntimeException("Owner not found"));

        Business business = new Business();
        business.setName(businessDto.getName());
        business.setType(businessDto.getType());
        business.setOwner(owner);

        Business save = businessRepo.save(business);

        return new BusinessDto(save.getId(), save.getName(),save.getType(), owner.getId());
    }

    @Override
    public BusinessDto updateBusiness(Integer id, BusinessDto businessDto) {
        Optional<Business> byId = businessRepo.findById(id);
        if (byId.isPresent()) {
            Business business = byId.get();
            business.setName(businessDto.getName());
            business.setType(businessDto.getType());
            Business updatedBusiness = businessRepo.save(business);
            return new BusinessDto(updatedBusiness.getId(), updatedBusiness.getName(), updatedBusiness.getType(), updatedBusiness.getOwner().getId());
        }
        return null;
    }

    @Override
    public BusinessDto deleteBusiness(Integer id) {
        Optional<Business> byId = businessRepo.findById(id);
        if (byId.isPresent()) {
            businessRepo.deleteById(id);
            Business business = byId.get();
            return new BusinessDto(business.getId(), business.getName(), business.getType(), business.getOwner().getId());
        }
        return null;
    }

    @Override
    public BusinessDto getBusinessById(Integer id) {
        Optional<Business> byId = businessRepo.findById(id);
        if (byId.isPresent()) {
            Business businessId = byId.get();
            return new BusinessDto(businessId.getId(), businessId.getName(), businessId.getType(), businessId.getOwner().getId());
        }
        return null;
    }

    @Override
    public List<BusinessDto> getAllBusiness() {
        List<Business> all = businessRepo.findAll();
        return all.stream()
                .map(business -> new BusinessDto(business.getId(), business.getName(),business.getType(), business.getOwner().getId()))
                .toList();
    }

    @Override
    public List<BusinessDto> getBusinessByOwnerId(Integer ownerId) {
        BizOwner owner = bizOwnerRepo.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        List<Business> businessList = businessRepo.findByOwnerId(owner.getId());

        return businessList.stream()
                .map(b -> new BusinessDto(
                        b.getId(),
                        b.getName(),
                        b.getType(),
                        b.getOwner().getId()
                ))
                .toList();
    }

}
