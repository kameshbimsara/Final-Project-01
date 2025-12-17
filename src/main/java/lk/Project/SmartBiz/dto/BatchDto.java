package lk.Project.SmartBiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchDto {
    private Integer id;
    private LocalDate manufactureDate;
    private LocalDate expireDate;
    private Double unitPrice;
    private Integer quantity;
    private Integer productId;
    private Integer supplierId;
}
