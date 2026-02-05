package lk.Project.smart_biz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchResponseDto {
    private Integer id;
    private LocalDate manufactureDate;
    private LocalDate expireDate;
    private Double unitPrice;
    private Integer quantity;
    private Integer supplierId;
}
