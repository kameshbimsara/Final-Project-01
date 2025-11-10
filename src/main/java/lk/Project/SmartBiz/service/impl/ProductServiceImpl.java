package lk.Project.SmartBiz.service.impl;

import lk.Project.SmartBiz.dto.ProductDto;
import lk.Project.SmartBiz.entity.Product;
import lk.Project.SmartBiz.repo.ProductRepo;
import lk.Project.SmartBiz.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public ProductDto saveProduct(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setDescription(dto.getDescription());
        product.setQuantity(dto.getQuantity() != null ? dto.getQuantity() : 0);
        Product saved = productRepo.save(product);
        dto.setId(saved.getId());
        return dto;
    }

    @Override
    public ProductDto updateProduct(Integer id, ProductDto dto) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setDescription(dto.getDescription());
        if (dto.getQuantity() != null) product.setQuantity(dto.getQuantity());
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
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepo.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ProductDto mapToDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getDescription(),
                product.getQuantity()
        );
    }
}
