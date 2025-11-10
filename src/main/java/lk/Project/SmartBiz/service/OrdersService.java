package lk.Project.SmartBiz.service;

import lk.Project.SmartBiz.dto.OrdersDto;
import java.util.List;

public interface OrdersService {
    OrdersDto saveOrder(OrdersDto dto);
    OrdersDto updateOrder(Integer id, OrdersDto dto);
    void deleteOrder(Integer id);
    OrdersDto getOrderById(Integer id);
    List<OrdersDto> getAllOrders();
}
