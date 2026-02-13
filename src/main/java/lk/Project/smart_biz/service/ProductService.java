package lk.Project.smart_biz.service;

import lk.Project.smart_biz.dto.ProductDto;
import lk.Project.smart_biz.dto.ProductWithBatchDto;
import lk.Project.smart_biz.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductDto saveProduct(ProductDto dto);
    ProductDto updateProduct(Integer id, ProductDto dto);
    void deleteProduct(Integer id);
    ProductDto getProductById(Integer id);
    List<ProductDto> getAllProducts();
    ResponseEntity<ProductWithBatchDto> findByNameAndBusiness_Id(String name , Integer businessId);
    ResponseEntity<ProductDto> findProductByNameAndBusinessId(String name, Integer businessId);
    List<ProductDto> getProductsByBusinessId(Integer businessId);
}
