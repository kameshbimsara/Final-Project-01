package lk.Project.SmartBiz.service;

import lk.Project.SmartBiz.dto.BizSupplerDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BizSupplerService {

    BizSupplerDto saveBizSuppler(BizSupplerDto bizSupplerDto);
    BizSupplerDto updateBizSuppler(BizSupplerDto bizSupplerDto);
    BizSupplerDto deleteBizSuppler(Integer id);
    BizSupplerDto getBizSupplerById(Integer id);
    List<BizSupplerDto> getAllBizSuppler();

}
