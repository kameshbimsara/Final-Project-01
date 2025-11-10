package lk.Project.SmartBiz.service;

import lk.Project.SmartBiz.dto.ProductDto;
import java.util.List;

public interface ProductService {
    ProductDto saveProduct(ProductDto dto);
    ProductDto updateProduct(Integer id, ProductDto dto);
    void deleteProduct(Integer id);
    ProductDto getProductById(Integer id);
    List<ProductDto> getAllProducts();
}
