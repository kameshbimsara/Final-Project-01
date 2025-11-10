package lk.Project.SmartBiz.service.impl;

import lk.Project.SmartBiz.dto.BusinessDto;
import lk.Project.SmartBiz.entity.Business;
import lk.Project.SmartBiz.repo.BusinessRepo;
import lk.Project.SmartBiz.service.BusinessService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessServiceImpl implements BusinessService {

    BusinessRepo businessRepo;

    public BusinessServiceImpl(BusinessRepo businessRepo) {
        this.businessRepo = businessRepo;
    }

    @Override
    public BusinessDto saveBusiness(BusinessDto businessDto) {
        Business business = new Business(null, businessDto.getName());
        Business save = businessRepo.save(business);
        return new BusinessDto(save.getId(), save.getName());
    }

    @Override
    public BusinessDto updateBusiness(BusinessDto businessDto) {
        Optional<Business> byId = businessRepo.findById(businessDto.getId());
        if (byId.isPresent()) {
            Business business = byId.get();
            business.setName(businessDto.getName());
            Business update = businessRepo.save(business);
            return new BusinessDto(update.getId(), update.getName());
        }
        return null;
    }

    @Override
    public BusinessDto deleteBusiness(Integer id) {
        Optional<Business> byId = businessRepo.findById(id);
        if (byId.isPresent()) {
            businessRepo.deleteById(id);
            Business business = byId.get();
            return new BusinessDto(business.getId(), business.getName());
        }
        return null;
    }

    @Override
    public BusinessDto getBusinessById(Integer id) {
        Optional<Business> byId = businessRepo.findById(id);
        if (byId.isPresent()) {
            Business businessId = byId.get();
            return new BusinessDto(businessId.getId(), businessId.getName());
        }
        return null;
    }

    @Override
    public List<BusinessDto> getAllBusiness() {
        List<Business> all = businessRepo.findAll();
        return all.stream()
                .map(business -> new BusinessDto(business.getId(), business.getName()))
                .toList();
    }
}
