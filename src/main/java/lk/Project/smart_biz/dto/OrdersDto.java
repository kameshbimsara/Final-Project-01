package lk.Project.smart_biz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<OrderDetailsDto> orderDetails;

}
