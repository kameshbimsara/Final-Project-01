package lk.Project.SmartBiz.controller;

import lk.Project.SmartBiz.dto.BizSupplerDto;
import lk.Project.SmartBiz.entity.BizSuppler;
import lk.Project.SmartBiz.service.BizSupplerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        BizSupplerDto supplerSave = bizSupplerService.saveBizSuppler(bizSupplerDto);
        return supplerSave;
    }
    @PutMapping("/{id}")
    public BizSupplerDto updateBizSuppler(@PathVariable("id") Integer id , @RequestBody BizSupplerDto bizSupplerDto) {
        bizSupplerDto.setId(id);
        BizSupplerDto supplerUpdate = bizSupplerService.updateBizSuppler(bizSupplerDto);
        return supplerUpdate;
    }
    @DeleteMapping("/{id}")
    public BizSupplerDto deleteBizSuppler(@PathVariable("id") Integer id) {
        BizSupplerDto supplerDelete = bizSupplerService.deleteBizSuppler(id);
        return supplerDelete;
    }
    @GetMapping("/{id}")
    public BizSupplerDto getBizSupplerById(@PathVariable("id") Integer id) {
        BizSupplerDto bizSupplerById = bizSupplerService.getBizSupplerById(id);
        return bizSupplerById;
    }
    @GetMapping
    public List<BizSupplerDto> getAllBizSuppler() {
        List<BizSupplerDto> allBizSuppler = bizSupplerService.getAllBizSuppler();
        return allBizSuppler;
    }

}
