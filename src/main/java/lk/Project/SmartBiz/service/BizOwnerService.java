package lk.Project.SmartBiz.service;

import lk.Project.SmartBiz.dto.BizOwnerDto;
import lk.Project.SmartBiz.dto.BizOwnerDtoReturn;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BizOwnerService {
    BizOwnerDto saveBizOwner(BizOwnerDto bizOwnerDto);
    BizOwnerDtoReturn updateBizOwner(BizOwnerDto bizOwnerDto);
    BizOwnerDto deleteBizOwner(Integer id);
    BizOwnerDto getBizOwnerById(Integer id);
    List<BizOwnerDtoReturn> getAllBizOwners();
}
