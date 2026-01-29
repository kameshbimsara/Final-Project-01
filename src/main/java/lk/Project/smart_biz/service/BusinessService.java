package lk.Project.smart_biz.service;

import lk.Project.smart_biz.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusinessService {
    BusinessDto saveBusiness(BusinessDto businessDto);
    BusinessDto updateBusiness(Integer id, BusinessDto businessDto);
    BusinessDto deleteBusiness(Integer id);
    BusinessDto getBusinessById(Integer id);
    List<BusinessDto> getAllBusiness();
    void updateStatus(Integer id, Integer status);
    BusinessLoginResponseDto login(BusinessLoginDto businessLoginDto);


}
