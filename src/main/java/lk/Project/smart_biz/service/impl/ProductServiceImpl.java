package lk.Project.smart_biz.service.impl;

import lk.Project.smart_biz.dto.BatchResponseDto;
import lk.Project.smart_biz.dto.ProductDto;
import lk.Project.smart_biz.dto.ProductWithBatchDto;
import lk.Project.smart_biz.entity.Business;
import lk.Project.smart_biz.entity.Product;
import lk.Project.smart_biz.repo.BusinessRepo;
import lk.Project.smart_biz.repo.ProductRepo;
import lk.Project.smart_biz.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepo productRepo;
    private final BusinessRepo businessRepo;

    public ProductServiceImpl(ProductRepo productRepo, BusinessRepo businessRepo) {
        this.productRepo = productRepo;
        this.businessRepo = businessRepo;
    }

    @Override
    public ProductDto saveProduct(ProductDto dto) {

        if (dto.getBusinessId() == null) {
            throw new RuntimeException("Business ID is required");
        }

        Product product = new Product();
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setDescription(dto.getDescription());

        Business business = businessRepo.findById(dto.getBusinessId()).orElseThrow(() -> new RuntimeException("Business not found"));

        product.setBusiness(business);

        Product saved = productRepo.save(product);
        dto.setId(saved.getId());

        return dto;
    }

    @Override
    public ProductDto updateProduct(Integer id, ProductDto dto) {

        Product product = productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setDescription(dto.getDescription());

        if (dto.getBusinessId() != null) {
            Business business = businessRepo.findById(dto.getBusinessId()).orElseThrow(() -> new RuntimeException("Business not found"));
            product.setBusiness(business);
        }

        productRepo.save(product);
        dto.setId(product.getId());

        return dto;
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepo.deleteById(id);
    }

    @Override
    public ProductDto getProductById(Integer id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepo.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<ProductWithBatchDto> findByNameAndBusiness_Id(String productName, Integer businessId) {
        Optional<Product> productOpt = productRepo.findByNameAndBusiness_Id(productName, businessId);
        if (productOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product product = productOpt.get();
        ProductWithBatchDto responseDto = new ProductWithBatchDto();
        responseDto.setId(product.getId());
        responseDto.setName(product.getName());
        responseDto.setBrand(product.getBrand());
        responseDto.setDescription(product.getDescription());

        List<BatchResponseDto> batchDtos = product.getBatches().stream().map(batch -> {
            BatchResponseDto batchDto = new BatchResponseDto();
            batchDto.setId(batch.getId());
            batchDto.setQuantity(batch.getQuantity());
            batchDto.setUnitPrice(batch.getUnitPrice());
            batchDto.setManufactureDate(batch.getManufactureDate());
            batchDto.setExpireDate(batch.getExpireDate());
            batchDto.setSupplierId(batch.getBizSuppler().getId());
            return batchDto;
        }).collect(Collectors.toList());

        responseDto.setBatches(batchDtos);

        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<ProductDto> findProductByNameAndBusinessId(String name, Integer businessId) {
        Optional<Product> productOpt = productRepo.findByNameAndBusiness_Id(name, businessId);
        if (productOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ProductDto dto = mapToDto(productOpt.get());
        return ResponseEntity.ok(dto);
    }

    @Override
    public List<ProductDto> getProductsByBusinessId(Integer businessId) {
        List<Product> products = productRepo.findByBusinessId(businessId);

        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(mapToDto(product));
        }
        return productDtos;
    }

    private ProductDto mapToDto(Product product) {
        ProductDto dto = new ProductDto(product.getId(), product.getName(), product.getBrand(), product.getDescription());
        if (product.getBusiness() != null) dto.setBusinessId(product.getBusiness().getId());
        return dto;
    }
}

