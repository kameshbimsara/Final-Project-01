package lk.Project.SmartBiz.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDto {

    private Integer id;
    private Integer customerId;
    private LocalDate date;
    private Integer totalAmount;
    private List<OrderDetailsDto> orderDetails;

    public OrdersDto(Integer id, Integer customerId, Integer totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
    }
}
