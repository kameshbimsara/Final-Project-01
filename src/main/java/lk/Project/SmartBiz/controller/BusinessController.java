package lk.Project.SmartBiz.controller;

import lk.Project.SmartBiz.dto.BusinessDto;
import lk.Project.SmartBiz.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/business")
@CrossOrigin
public class BusinessController {

    private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @PostMapping
    public BusinessDto addBusiness(@RequestBody BusinessDto businessDto) {
        return businessService.saveBusiness(businessDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessDto> updateBusiness(@PathVariable Integer id, @RequestBody BusinessDto businessDto) {
        BusinessDto update = businessService.updateBusiness(id, businessDto);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public BusinessDto deleteBusiness(@PathVariable("id") Integer id) {
        return businessService.deleteBusiness(id);
    }

    @GetMapping("/{id}")
    public BusinessDto getBusinessById(@PathVariable("id") Integer id) {
        return businessService.getBusinessById(id);
    }

    @GetMapping
    public List<BusinessDto> getAllBusiness() {
        return businessService.getAllBusiness();
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<BusinessDto>> getBusinessByOwner(@PathVariable Integer ownerId) {
        return ResponseEntity.ok(businessService.getBusinessByOwnerId(ownerId));
    }
}
