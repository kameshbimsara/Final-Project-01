package lk.Project.SmartBiz.service;

import lk.Project.SmartBiz.dto.BizOwnerDto;
import lk.Project.SmartBiz.dto.BizOwnerDtoReturn;
import lk.Project.SmartBiz.dto.BizOwnerLoginRequest;
import lk.Project.SmartBiz.dto.BizOwnerLoginResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BizOwnerService {
    BizOwnerDtoReturn saveBizOwner(BizOwnerDto bizOwnerDto);
    BizOwnerDtoReturn updateBizOwner(BizOwnerDto bizOwnerDto);
    BizOwnerDto deleteBizOwner(Integer id);
    BizOwnerDto getBizOwnerById(Integer id);
    List<BizOwnerDtoReturn> getAllBizOwners();

    BizOwnerLoginResponse login(BizOwnerLoginRequest request);
}
