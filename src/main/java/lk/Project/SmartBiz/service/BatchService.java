package lk.Project.SmartBiz.service;

import lk.Project.SmartBiz.dto.BatchDto;
import java.util.List;

public interface BatchService {
    BatchDto saveBatch(BatchDto dto);
    BatchDto updateBatch(Integer id, BatchDto dto);
    void deleteBatch(Integer id);
    BatchDto getBatchById(Integer id);
    List<BatchDto> getAllBatches();
    List<BatchDto> getBatchesByProduct(Integer productId);
}
