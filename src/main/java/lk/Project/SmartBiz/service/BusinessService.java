package lk.Project.SmartBiz.service;

import lk.Project.SmartBiz.dto.BusinessDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusinessService {
    BusinessDto saveBusiness(BusinessDto businessDto);
    BusinessDto updateBusiness(BusinessDto businessDto);
    BusinessDto deleteBusiness(Integer id);
    BusinessDto getBusinessById(Integer id);
    List<BusinessDto> getAllBusiness();
}
