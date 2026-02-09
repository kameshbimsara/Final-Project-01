package lk.Project.smart_biz.service;

import lk.Project.smart_biz.dto.BizSupplerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BizSupplerService {

    BizSupplerDto saveBizSuppler(BizSupplerDto bizSupplerDto);
    BizSupplerDto updateBizSuppler(BizSupplerDto bizSupplerDto);
    BizSupplerDto deleteBizSuppler(Integer id);
    BizSupplerDto getBizSupplerById(Integer id);
    List<BizSupplerDto> getAllBizSuppler();
    List<BizSupplerDto> getSuppliersByBusinessId(Integer businessId);
    ResponseEntity<BizSupplerDto> getSupplierByPhoneNumber(String phoneNumber, Integer businessId);


}
