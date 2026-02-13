package lk.Project.smart_biz.controller;

import lk.Project.smart_biz.dto.ProductDto;
import lk.Project.smart_biz.dto.ProductRequestDto;
import lk.Project.smart_biz.dto.ProductWithBatchDto;
import lk.Project.smart_biz.entity.Product;
import lk.Project.smart_biz.service.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto dto) {
        return productService.saveProduct(dto);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable Integer id, @RequestBody ProductDto dto) {
        return productService.updateProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/product_with_batches")
    public ResponseEntity<ProductWithBatchDto> productWithBatches(@RequestBody ProductRequestDto productRequestDto) {
        return productService.findByNameAndBusiness_Id(productRequestDto.getProductName(), productRequestDto.getBusinessId());
    }

    @PostMapping("/productName")
    public ResponseEntity<ProductDto> getProductByNameAndBusinessId(@RequestBody ProductRequestDto productRequestDto) {
        return productService.findProductByNameAndBusinessId(productRequestDto.getProductName(), productRequestDto.getBusinessId());
    }

    @GetMapping("/business/{businessId}")
    public List<ProductDto> getProductsByBusinessId(@PathVariable Integer businessId) {
        return productService.getProductsByBusinessId(businessId);
    }

}
