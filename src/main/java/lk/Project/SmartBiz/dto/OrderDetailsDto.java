package lk.Project.SmartBiz.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Integer amount;
}
