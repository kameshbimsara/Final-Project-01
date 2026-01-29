package lk.Project.smart_biz.service.impl;

import lk.Project.smart_biz.dto.ProductDto;
import lk.Project.smart_biz.entity.Business;
import lk.Project.smart_biz.entity.Product;
import lk.Project.smart_biz.repo.BusinessRepo;
import lk.Project.smart_biz.repo.ProductRepo;
import lk.Project.smart_biz.service.ProductService;
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

    public ProductServiceImpl(ProductRepo productRepo, BusinessRepo businessRepo) {
        this.productRepo = productRepo;
        this.businessRepo = businessRepo;
    }

    @Override
    public ProductDto saveProduct(ProductDto dto) {

        log.info("Saving new product: {}", dto);

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

        log.info("Updating product ID {} with data: {}", id, dto);

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
        Product product = productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        log.info("Fetching all products");
        return productRepo.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private ProductDto mapToDto(Product product) {
        ProductDto dto = new ProductDto(product.getId(), product.getName(), product.getBrand(), product.getDescription());

        if (product.getBusiness() != null) dto.setBusinessId(product.getBusiness().getId());

        return dto;
    }
}

