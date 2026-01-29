package lk.Project.smart_biz.service;

import lk.Project.smart_biz.dto.ProductDto;
import java.util.List;

public interface ProductService {
    ProductDto saveProduct(ProductDto dto);
    ProductDto updateProduct(Integer id, ProductDto dto);
    void deleteProduct(Integer id);
    ProductDto getProductById(Integer id);
    List<ProductDto> getAllProducts();
}
