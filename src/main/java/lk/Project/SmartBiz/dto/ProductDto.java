package lk.Project.SmartBiz.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Integer id;
    private String brand;
    private String name;
    private String description;
    private Integer quantity;
    private Integer supplierId;

    public ProductDto(Integer id, String name, String brand, String description, Integer quantity) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.quantity = quantity;
    }


}