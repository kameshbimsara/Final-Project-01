package lk.Project.SmartBiz.service;

import lk.Project.SmartBiz.dto.OrderDetailsDto;
import java.util.List;

public interface OrderDetailsService {
    OrderDetailsDto saveOrderDetail(OrderDetailsDto dto);
    OrderDetailsDto updateOrderDetail(Integer id, OrderDetailsDto dto);
    void deleteOrderDetail(Integer id);
    OrderDetailsDto getOrderDetailById(Integer id);
    List<OrderDetailsDto> getAllOrderDetails();
    List<OrderDetailsDto> getOrderDetailsByOrder(Integer orderId);
}
