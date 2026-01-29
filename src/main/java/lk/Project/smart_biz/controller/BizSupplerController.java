package lk.Project.smart_biz.controller;

import lk.Project.smart_biz.dto.BizSupplerDto;
import lk.Project.smart_biz.service.BizSupplerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bizsuppler")
public class BizSupplerController {

    BizSupplerService bizSupplerService;

    @Autowired
    public BizSupplerController(BizSupplerService bizSupplerService) {
        this.bizSupplerService = bizSupplerService;
    }

    @PostMapping
    public BizSupplerDto saveBizSuppler(@RequestBody BizSupplerDto bizSupplerDto) {
        return bizSupplerService.saveBizSuppler(bizSupplerDto);
    }
    @PutMapping("/{id}")
    public BizSupplerDto updateBizSuppler(@PathVariable("id") Integer id , @RequestBody BizSupplerDto bizSupplerDto) {
        bizSupplerDto.setId(id);
        return bizSupplerService.updateBizSuppler(bizSupplerDto);
    }
    @DeleteMapping("/{id}")
    public BizSupplerDto deleteBizSuppler(@PathVariable("id") Integer id) {
        return bizSupplerService.deleteBizSuppler(id);
    }
    @GetMapping("/{id}")
    public BizSupplerDto getBizSupplerById(@PathVariable("id") Integer id) {
        return bizSupplerService.getBizSupplerById(id);
    }
    @GetMapping
    public List<BizSupplerDto> getAllBizSuppler() {
        return bizSupplerService.getAllBizSuppler();
    }

    @GetMapping("/name/{companyName}")
    public BizSupplerDto getBizSupplerByName(@PathVariable String companyName) {
        return bizSupplerService.getBizSupplerByName(companyName);
    }

}
