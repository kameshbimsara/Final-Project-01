package lk.Project.smart_biz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithBatchDto {
    private Integer id;
    private String brand;
    private String name;
    private String description;
    private List<BatchResponseDto> batches;
}
