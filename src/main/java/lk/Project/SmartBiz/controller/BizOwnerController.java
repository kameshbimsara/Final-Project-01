package lk.Project.SmartBiz.controller;

import lk.Project.SmartBiz.dto.BizOwnerDto;
import lk.Project.SmartBiz.dto.BizOwnerDtoReturn;
import lk.Project.SmartBiz.dto.BizOwnerLoginRequest;
import lk.Project.SmartBiz.dto.BizOwnerLoginResponse;
import lk.Project.SmartBiz.service.BizOwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/business/biz/bizowner")
@CrossOrigin
public class BizOwnerController {

    private final BizOwnerService bizOwnerService;

    public BizOwnerController(BizOwnerService bizOwnerService) {
        this.bizOwnerService = bizOwnerService;
    }

    @PostMapping
    public BizOwnerDtoReturn addBizOwner(@RequestBody BizOwnerDto bizOwnerDto) {
        return bizOwnerService.saveBizOwner(bizOwnerDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BizOwnerDtoReturn> updateBizOwner(@PathVariable Integer id, @RequestBody BizOwnerDto bizOwnerDto) {
        bizOwnerDto.setId(id);
        BizOwnerDtoReturn bizUpdate = bizOwnerService.updateBizOwner(bizOwnerDto);
        return new ResponseEntity<>(bizUpdate, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public BizOwnerDto deleteBizOwner(@PathVariable Integer id) {
        return bizOwnerService.deleteBizOwner(id);
    }

    @GetMapping("/{id}")
    public BizOwnerDto getOwnerById(@PathVariable Integer id) {
        return bizOwnerService.getBizOwnerById(id);
    }

    @GetMapping
    public List<BizOwnerDtoReturn> getAllOwners() {
        return bizOwnerService.getAllBizOwners();
    }

    @PostMapping("/login")
    public BizOwnerLoginResponse login(@RequestBody BizOwnerLoginRequest request) {
        return bizOwnerService.login(request);
    }

}
