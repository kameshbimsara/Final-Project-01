package lk.Project.SmartBiz.controller;

import lk.Project.SmartBiz.dto.BatchDto;
import lk.Project.SmartBiz.service.BatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/batches")
@CrossOrigin
public class BatchController {

    private final BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @PostMapping
    public BatchDto createBatch(@RequestBody BatchDto dto) {
        return batchService.saveBatch(dto);
    }

    @PutMapping("/{id}")
    public BatchDto updateBatch(@PathVariable Integer id, @RequestBody BatchDto dto) {
        return batchService.updateBatch(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBatch(@PathVariable Integer id) {
        batchService.deleteBatch(id);
    }

    @GetMapping("/{id}")
    public BatchDto getBatchById(@PathVariable Integer id) {
        return batchService.getBatchById(id);
    }

    @GetMapping
    public List<BatchDto> getAllBatches() {
        return batchService.getAllBatches();
    }

    @GetMapping("/product/{productId}")
    public List<BatchDto> getBatchesByProduct(@PathVariable Integer productId) {
        return batchService.getBatchesByProduct(productId);
    }
}
