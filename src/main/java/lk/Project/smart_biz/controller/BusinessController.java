package lk.Project.smart_biz.controller;

import lk.Project.smart_biz.dto.*;
import lk.Project.smart_biz.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

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
        System.out.println(businessDto.getStatus());
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

    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateBusinessStatus(@PathVariable Integer id, @RequestBody Map<String, Integer> body) {

        Integer status = body.get("status");
        businessService.updateStatus(id, status);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public BusinessLoginResponseDto login(@RequestBody BusinessLoginDto businessLoginDto) {
        return businessService.login(businessLoginDto);
    }

    }

