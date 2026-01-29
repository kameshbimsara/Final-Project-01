package lk.Project.smart_biz.service.impl;

import jakarta.transaction.Transactional;
import lk.Project.smart_biz.dto.OrderDetailsDto;
import lk.Project.smart_biz.dto.OrdersDto;
import lk.Project.smart_biz.entity.*;
import lk.Project.smart_biz.repo.BusinessRepo;
import lk.Project.smart_biz.repo.CustomerRepo;
import lk.Project.smart_biz.repo.OrdersRepo;
import lk.Project.smart_biz.repo.ProductRepo;
import lk.Project.smart_biz.service.OrdersService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepo ordersRepo;
    private final CustomerRepo customerRepo;
    private final BusinessRepo businessRepo;
    private final ProductRepo productRepo;

    public OrdersServiceImpl(OrdersRepo ordersRepo, CustomerRepo customerRepo, BusinessRepo businessRepo, ProductRepo productRepo) {
        this.ordersRepo = ordersRepo;
        this.customerRepo = customerRepo;
        this.businessRepo = businessRepo;
        this.productRepo = productRepo;
    }

    @Override
    @Transactional
    public OrdersDto saveOrder(OrdersDto dto) {
        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Business business = businessRepo.findById(dto.getBusinessId())
                .orElseThrow(() -> new RuntimeException("Business not found"));
        Product product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Orders order = new Orders();
        order.setId(dto.getId());
        order.setCustomer(customer);
        order.setBusiness(business);
        order.setProduct(product);
        order.setDate(LocalDate.now());
        order.setTotalAmount(dto.getTotalAmount());

        List<OrderDetails> orderDetailsList = dto.getOrderDetails().stream()
                .map(orderDetails -> {
                    OrderDetails orderDetail = new OrderDetails();
                    orderDetail.setId(orderDetails.getId());
                    orderDetail.setQuantity(orderDetails.getQuantity());
                    orderDetail.setPrice(orderDetails.getPrice());
                    orderDetail.setOrder(order);
                    return orderDetail;
                })
                .toList();
        order.setOrder_details(orderDetailsList);

        Orders savedOrder = ordersRepo.save(order);

        return new OrdersDto(savedOrder.getId(),savedOrder.getDate(),savedOrder.getTotalAmount(), savedOrder.getBusiness().getId(),savedOrder.getCustomer().getId(),savedOrder.getProduct().getId(),dto.getOrderDetails());


    }

    @Override
    @Transactional
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
        order.setProduct(dto.getProductId() != null ?
                productRepo.findById(dto.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found")) : order.getProduct());

        if (dto.getOrderDetails() != null) {
            order.getOrder_details().clear();

            dto.getOrderDetails().forEach(orderDto -> {
                OrderDetails orderDetail = new OrderDetails();
                orderDetail.setQuantity(orderDto.getQuantity());
                orderDetail.setPrice(orderDto.getPrice());
                orderDetail.setOrder(order);
                order.getOrder_details().add(orderDetail);
            });
        }

        Orders saved = ordersRepo.save(order);

        return new OrdersDto(saved.getId(),saved.getDate(),saved.getTotalAmount(),saved.getBusiness().getId(),saved.getCustomer().getId(),saved.getProduct().getId(),dto.getOrderDetails());
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

        ArrayList<OrderDetailsDto> orderDetailsDtos = new ArrayList<>();

        for (OrderDetails ent : order.getOrder_details()) {
            OrderDetailsDto dto = new OrderDetailsDto(ent.getId(), ent.getQuantity(), ent.getPrice(), ent.getOrder().getId());
            orderDetailsDtos.add(dto);
        }

        return new OrdersDto(order.getId(),order.getDate(), order.getTotalAmount(), order.getBusiness().getId(), order.getCustomer().getId(),order.getProduct().getId(),orderDetailsDtos);
    }

    @Override
    public List<OrdersDto> getAllOrders() {
        return ordersRepo.findAll().stream()
                .map(order -> new OrdersDto(
                        order.getId(),
                        order.getDate(),
                        order.getTotalAmount(),
                        order.getBusiness().getId(),
                        order.getCustomer().getId(),
                        order.getProduct().getId(),
                        order.getOrder_details().stream().map(orderDetails
                                -> new OrderDetailsDto(orderDetails.getId(),
                                orderDetails.getQuantity(),orderDetails.getPrice(),orderDetails.getOrder().getId())).collect(Collectors.toList())
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
                        order.getCustomer().getId(),
                        order.getProduct().getId(),
                        order.getOrder_details().stream().map(orderDetails
                                -> new OrderDetailsDto(orderDetails.getId(), orderDetails.getQuantity(),
                                orderDetails.getPrice(), orderDetails.getId())).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

}
