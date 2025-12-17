package lk.Project.SmartBiz.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDto {

    private Integer id;
    private LocalDate date;
    private Integer totalAmount;
    private Integer businessId;
    private Integer customerId;

    public OrdersDto(Integer customerId, LocalDate date, Integer totalAmount, Integer businessId) {
        this.customerId = customerId;
        this.date = date;
        this.totalAmount = totalAmount;
        this.businessId = businessId;
    }
}
