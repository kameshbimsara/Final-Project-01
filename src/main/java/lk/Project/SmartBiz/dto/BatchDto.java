package lk.Project.SmartBiz.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BatchDto {
    private Integer id;
    private LocalDate manufactureDate;
    private LocalDate expireDate;
    private Double unitPrice;
    private Integer quantity;
    private Integer productId;
}
