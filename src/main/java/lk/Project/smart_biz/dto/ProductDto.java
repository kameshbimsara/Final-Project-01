package lk.Project.smart_biz.dto;

import lombok.Data;
import org.hibernate.mapping.List;

@Data
public class ProductDto {
    private Integer id;
    private String brand;
    private String description;
    private String name;
    private Integer businessId;

    public ProductDto(Integer id, String name, String brand, String description) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
    }
}
