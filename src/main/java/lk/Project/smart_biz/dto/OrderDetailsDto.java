package lk.Project.smart_biz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto {
    private Integer id;
    private Integer quantity;
    private Integer productId;
    private Double unitPrice;
    private Double price;
    private Integer orderId;
}
