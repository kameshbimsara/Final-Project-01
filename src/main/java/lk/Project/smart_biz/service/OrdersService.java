package lk.Project.smart_biz.service;

import lk.Project.smart_biz.dto.OrdersDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface OrdersService {
    OrdersDto saveOrder(OrdersDto dto);
    OrdersDto updateOrder(Integer id, OrdersDto dto);
    void deleteOrder(Integer id);
    OrdersDto getOrderById(Integer id);
    List<OrdersDto> getAllOrders();
    List<OrdersDto> getOrdersByCustomer(Integer customerId);
    List<OrdersDto> getOrdersByDate(LocalDate date, Integer businessId);
}
