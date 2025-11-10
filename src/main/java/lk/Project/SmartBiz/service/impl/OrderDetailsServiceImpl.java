package lk.Project.SmartBiz.service.impl;

import lk.Project.SmartBiz.dto.OrderDetailsDto;
import lk.Project.SmartBiz.entity.OrderDetails;
import lk.Project.SmartBiz.entity.Orders;
import lk.Project.SmartBiz.repo.OrderDetailsRepo;
import lk.Project.SmartBiz.repo.OrdersRepo;
import lk.Project.SmartBiz.service.OrderDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderDetailsRepo orderDetailsRepo;
    private final OrdersRepo ordersRepo;

    public OrderDetailsServiceImpl(OrderDetailsRepo orderDetailsRepo, OrdersRepo ordersRepo) {
        this.orderDetailsRepo = orderDetailsRepo;
        this.ordersRepo = ordersRepo;
    }

    @Override
    public OrderDetailsDto saveOrderDetail(OrderDetailsDto dto) {
        Orders order = ordersRepo.findById(dto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setAmount(dto.getAmount());
        orderDetails.setQuantity(dto.getQuantity());
        orderDetails.setOrder(order);

        OrderDetails saved = orderDetailsRepo.save(orderDetails);
        dto.setId(saved.getId());
        return dto;
    }

    @Override
    public OrderDetailsDto updateOrderDetail(Integer id, OrderDetailsDto dto) {
        OrderDetails existing = orderDetailsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Detail not found"));

        existing.setAmount(dto.getAmount());
        existing.setQuantity(dto.getQuantity());
        orderDetailsRepo.save(existing);

        dto.setId(existing.getId());
        dto.setOrderId(existing.getOrder().getId());
        return dto;
    }

    @Override
    public void deleteOrderDetail(Integer id) {
        if (!orderDetailsRepo.existsById(id)) {
            throw new RuntimeException("Order Detail not found");
        }
        orderDetailsRepo.deleteById(id);
    }

    @Override
    public OrderDetailsDto getOrderDetailById(Integer id) {
        OrderDetails details = orderDetailsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Detail not found"));

        OrderDetailsDto dto = new OrderDetailsDto();
        dto.setId(details.getId());
        dto.setAmount(details.getAmount());
        dto.setQuantity(details.getQuantity());
        dto.setOrderId(details.getOrder().getId());
        return dto;
    }

    @Override
    public List<OrderDetailsDto> getAllOrderDetails() {
        return orderDetailsRepo.findAll().stream().map(details -> {
            OrderDetailsDto dto = new OrderDetailsDto();
            dto.setId(details.getId());
            dto.setAmount(details.getAmount());
            dto.setQuantity(details.getQuantity());
            dto.setOrderId(details.getOrder().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<OrderDetailsDto> getOrderDetailsByOrder(Integer orderId) {
        return orderDetailsRepo.findByOrderId(orderId).stream().map(details -> {
            OrderDetailsDto dto = new OrderDetailsDto();
            dto.setId(details.getId());
            dto.setAmount(details.getAmount());
            dto.setQuantity(details.getQuantity());
            dto.setOrderId(details.getOrder().getId());
            return dto;
        }).collect(Collectors.toList());
    }
}
