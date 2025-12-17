package lk.Project.SmartBiz.service.impl;

import lk.Project.SmartBiz.dto.OrdersDto;
import lk.Project.SmartBiz.entity.*;
import lk.Project.SmartBiz.repo.*;
import lk.Project.SmartBiz.service.OrdersService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepo ordersRepo;
    private final CustomerRepo customerRepo;
    private final BusinessRepo businessRepo;

    public OrdersServiceImpl(OrdersRepo ordersRepo, CustomerRepo customerRepo, BusinessRepo businessRepo) {
        this.ordersRepo = ordersRepo;
        this.customerRepo = customerRepo;
        this.businessRepo = businessRepo;
    }

    @Override
    public OrdersDto saveOrder(OrdersDto dto) {
        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Business business = businessRepo.findById(dto.getBusinessId())
                .orElseThrow(() -> new RuntimeException("Business not found"));

        Orders order = new Orders();
        order.setId(dto.getId());
        order.setCustomer(customer);
        order.setBusiness(business);
        order.setDate(dto.getDate());
        order.setTotalAmount(dto.getTotalAmount());

        Orders savedOrder = ordersRepo.save(order);
        return new OrdersDto(savedOrder.getId(),savedOrder.getDate(), savedOrder.getTotalAmount(),savedOrder.getCustomer().getId(), savedOrder.getBusiness().getId());


    }

    @Override
    public OrdersDto updateOrder(Integer id, OrdersDto dto) {
        Orders order = ordersRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setDate(dto.getDate() != null ? dto.getDate() : order.getDate());
        order.setTotalAmount(dto.getTotalAmount());
        order.setCustomer(dto.getCustomerId() != null ?
                customerRepo.findById(dto.getCustomerId())
                        .orElseThrow(() -> new RuntimeException("Customer not found")) : order.getCustomer());
        order.setBusiness(dto.getBusinessId() != null ?
                businessRepo.findById(dto.getBusinessId())
                        .orElseThrow(() -> new RuntimeException("Business not found")) : order.getBusiness());
        ordersRepo.save(order);

        return new OrdersDto(order.getId(),order.getDate(),order.getTotalAmount(),order.getBusiness().getId(), order.getCustomer().getId());
    }

    @Override
    public void deleteOrder(Integer id) {
        Orders order = ordersRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        ordersRepo.delete(order);
    }

    @Override
    public OrdersDto getOrderById(Integer id) {
        Orders order = ordersRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return new OrdersDto(order.getId(),order.getDate(), order.getTotalAmount(), order.getBusiness().getId(), order.getCustomer().getId());
    }

    @Override
    public List<OrdersDto> getAllOrders() {
        return ordersRepo.findAll().stream()
                .map(order -> new OrdersDto(
                        order.getId(),
                        order.getDate(),
                        order.getTotalAmount(),
                        order.getBusiness().getId(),
                        order.getCustomer().getId()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdersDto> getOrdersByCustomer(Integer customerId) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return ordersRepo.findByCustomer(customer).stream()
                .map(order -> new OrdersDto(
                        order.getId(),
                        order.getDate(),
                        order.getTotalAmount(),
                        order.getBusiness().getId(),
                        order.getCustomer().getId()
                ))
                .collect(Collectors.toList());
    }

}
