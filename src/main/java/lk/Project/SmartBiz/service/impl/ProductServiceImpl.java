package lk.Project.SmartBiz.service.impl;

import lk.Project.SmartBiz.dto.ProductDto;
import lk.Project.SmartBiz.entity.BizSuppler;
import lk.Project.SmartBiz.entity.Business;
import lk.Project.SmartBiz.entity.Product;
import lk.Project.SmartBiz.repo.BizSupplerRepo;
import lk.Project.SmartBiz.repo.BusinessRepo;
import lk.Project.SmartBiz.repo.ProductRepo;
import lk.Project.SmartBiz.service.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepo productRepo;
    private final BusinessRepo businessRepo;
    private final BizSupplerRepo supplierRepo;

    public ProductServiceImpl(ProductRepo productRepo,
                              BusinessRepo businessRepo,
                              BizSupplerRepo supplierRepo) {
        this.productRepo = productRepo;
        this.businessRepo = businessRepo;
        this.supplierRepo = supplierRepo;
    }

    @Override
    public ProductDto saveProduct(ProductDto dto) {

        log.info("Saving new product: {}", dto);

        try {
            Product product = new Product();
            product.setName(dto.getName());
            product.setBrand(dto.getBrand());
            product.setDescription(dto.getDescription());
            product.setQuantity(dto.getQuantity() != null ? dto.getQuantity() : 0);

            Business business = businessRepo.findById(dto.getBusinessId())
                    .orElseThrow(() -> new RuntimeException("Business not found"));
            product.setBusiness(business);

            if (dto.getSupplierId() != null) {
                BizSuppler supplier = supplierRepo.findById(dto.getSupplierId())
                        .orElseThrow(() -> new RuntimeException("Supplier not found"));
                product.setSupplier(supplier);
            }

            Product saved = productRepo.save(product);
            dto.setId(saved.getId());

            log.info("Product saved successfully with ID {}", saved.getId());

            return dto;

        } catch (Exception e) {
            log.error("Error saving product: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ProductDto updateProduct(Integer id, ProductDto dto) {

        log.info("Updating product ID {} with data: {}", id, dto);

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setDescription(dto.getDescription());
        if (dto.getQuantity() != null) product.setQuantity(dto.getQuantity());

        if (dto.getBusinessId() != null) {
            Business business = businessRepo.findById(dto.getBusinessId())
                    .orElseThrow(() -> new RuntimeException("Business not found"));
            product.setBusiness(business);
        }

        if (dto.getSupplierId() != null) {
            BizSuppler supplier = supplierRepo.findById(dto.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            product.setSupplier(supplier);
        }

        productRepo.save(product);
        dto.setId(product.getId());

        log.info("Product updated: ID {}", id);

        return dto;
    }

    @Override
    public void deleteProduct(Integer id) {
        log.warn("Deleting product ID {}", id);
        productRepo.deleteById(id);
    }

    @Override
    public ProductDto getProductById(Integer id) {
        log.info("Fetching product by ID {}", id);
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        log.info("Fetching all products");
        return productRepo.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ProductDto mapToDto(Product product) {
        ProductDto dto = new ProductDto(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getDescription(),
                product.getQuantity()
        );

        if (product.getBusiness() != null)
            dto.setBusinessId(product.getBusiness().getId());

        if (product.getSupplier() != null)
            dto.setSupplierId(product.getSupplier().getId());

        return dto;
    }
}

