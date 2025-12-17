package lk.Project.SmartBiz.service.impl;

import lk.Project.SmartBiz.dto.BatchDto;
import lk.Project.SmartBiz.entity.Batch;
import lk.Project.SmartBiz.entity.BizSuppler;
import lk.Project.SmartBiz.entity.Product;
import lk.Project.SmartBiz.repo.BatchRepo;
import lk.Project.SmartBiz.repo.BizSupplerRepo;
import lk.Project.SmartBiz.repo.ProductRepo;
import lk.Project.SmartBiz.service.BatchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchServiceImpl implements BatchService {

    private final BatchRepo batchRepo;
    private final ProductRepo productRepo;
    private final BizSupplerRepo supplerRepo;

    public BatchServiceImpl(BatchRepo batchRepo, ProductRepo productRepo , BizSupplerRepo supplerRepo) {
        this.batchRepo = batchRepo;
        this.productRepo = productRepo;
        this.supplerRepo = supplerRepo;
    }

    @Override
    @Transactional
    public BatchDto saveBatch(BatchDto dto) {
        Product product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Batch batch = new Batch();
        batch.setProduct(product);
        batch.setSupplier(product.getSupplier());
        batch.setQuantity(dto.getQuantity());
        batch.setUnitPrice(dto.getUnitPrice());
        batch.setManufactureDate(dto.getManufactureDate());
        batch.setExpireDate(dto.getExpireDate());

        Batch saved = batchRepo.save(batch);

        product.setQuantity(product.getQuantity() + dto.getQuantity());
        productRepo.save(product);

        dto.setId(saved.getId());
        return dto;
    }

    @Override
    @Transactional
    public BatchDto updateBatch(Integer id, BatchDto dto) {
        Batch batch = batchRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        batch.setManufactureDate(dto.getManufactureDate());
        batch.setExpireDate(dto.getExpireDate());
        batch.setUnitPrice(dto.getUnitPrice());
        batch.setQuantity(dto.getQuantity());

        if (dto.getProductId() != null) {
            Product product = productRepo.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            batch.setProduct(product);
        }

        if (dto.getSupplierId() != null) {
            BizSuppler supplier = supplerRepo.findById(dto.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            batch.setSupplier(supplier);
        }

        Batch updated = batchRepo.save(batch);
        return mapToDto(updated);
    }

    @Override
    @Transactional
    public void deleteBatch(Integer id) {
        Batch batch = batchRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        Product product = batch.getProduct();
        product.setQuantity(product.getQuantity() - batch.getQuantity());
        productRepo.save(product);

        batchRepo.delete(batch);
    }

    @Override
    public BatchDto getBatchById(Integer id) {
        Batch batch = batchRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        return mapToDto(batch);
    }

    @Override
    public List<BatchDto> getAllBatches() {
        return batchRepo.findAll().stream()
                .map(batch -> new BatchDto(
                        batch.getId(),
                        batch.getManufactureDate(),
                        batch.getExpireDate(),
                        batch.getUnitPrice(),
                        batch.getQuantity(),
                        batch.getProduct().getId(),
                        batch.getSupplier().getId()
                )).collect(Collectors.toList());
    }

    @Override
    public List<BatchDto> getBatchesByProduct(Integer productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return batchRepo.findByProduct(product).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private BatchDto mapToDto(Batch batch) {
        BatchDto dto = new BatchDto();
        dto.setId(batch.getId());
        dto.setProductId(batch.getProduct().getId());
        dto.setSupplierId(batch.getSupplier().getId());
        dto.setQuantity(batch.getQuantity());
        dto.setUnitPrice(batch.getUnitPrice());
        dto.setManufactureDate(batch.getManufactureDate());
        dto.setExpireDate(batch.getExpireDate());
        return dto;
    }
}
