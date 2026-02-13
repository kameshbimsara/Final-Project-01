package lk.Project.smart_biz.service.impl;

import lk.Project.smart_biz.dto.BatchDto;
import lk.Project.smart_biz.entity.Batch;
import lk.Project.smart_biz.entity.BizSuppler;
import lk.Project.smart_biz.entity.Business;
import lk.Project.smart_biz.entity.Product;
import lk.Project.smart_biz.repo.BatchRepo;
import lk.Project.smart_biz.repo.BizSupplerRepo;
import lk.Project.smart_biz.repo.BusinessRepo;
import lk.Project.smart_biz.repo.ProductRepo;
import lk.Project.smart_biz.service.BatchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchServiceImpl implements BatchService {

    private final BatchRepo batchRepo;
    private final ProductRepo productRepo;
    private final BizSupplerRepo supplerRepo;
    private final BusinessRepo businessRepo;

    public BatchServiceImpl(BatchRepo batchRepo, ProductRepo productRepo , BizSupplerRepo supplerRepo, BusinessRepo businessRepo) {
        this.businessRepo = businessRepo;
        this.batchRepo = batchRepo;
        this.productRepo = productRepo;
        this.supplerRepo = supplerRepo;
    }

    @Override
    @Transactional
    public BatchDto saveBatch(BatchDto dto) {
        Product product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        BizSuppler suppler = supplerRepo.findById(dto.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        Business business = businessRepo.findById(dto.getBusinessId())
                .orElseThrow(() -> new RuntimeException("Business not found"));

        Batch batch = new Batch();
        batch.setProduct(product);
        batch.setBusiness(business);
        batch.setQuantity(dto.getQuantity());
        batch.setUnitPrice(dto.getUnitPrice());
        batch.setManufactureDate(dto.getManufactureDate());
        batch.setExpireDate(dto.getExpireDate());
        batch.setBizSuppler(suppler);

        Batch saved = batchRepo.save(batch);

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

        Batch updated = batchRepo.save(batch);
        return mapToDto(updated);
    }

    @Override
    @Transactional
    public void deleteBatch(Integer id) {
        Batch batch = batchRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

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
                        batch.getBizSuppler().getId(),
                        batch.getBusiness().getId()
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

    @Override
    public List<BatchDto> getBatchesByBusinessId(Integer businessId) {
        List<Batch> batches = batchRepo.findByBusiness_Id(businessId);
        List<BatchDto> batchDtos = new ArrayList<>();
        for (Batch batch : batches) {
            batchDtos.add(mapToDto(batch));
        }
        return batchDtos;
    }

    private BatchDto mapToDto(Batch batch) {
        return new BatchDto(
                batch.getId(),
                batch.getManufactureDate(),
                batch.getExpireDate(),
                batch.getUnitPrice(),
                batch.getQuantity(),
                batch.getProduct().getId(),
                batch.getBizSuppler().getId(),
                batch.getBusiness().getId()
        );
    }
}
