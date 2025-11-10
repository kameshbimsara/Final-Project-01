package lk.Project.SmartBiz.service.impl;

import lk.Project.SmartBiz.dto.OrderDetailsDto;
import lk.Project.SmartBiz.dto.OrdersDto;
import lk.Project.SmartBiz.entity.*;
import lk.Project.SmartBiz.repo.*;
import lk.Project.SmartBiz.service.OrdersService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepo ordersRepo;
    private final CustomerRepo customerRepo;
    private final ProductRepo productRepo;
    private final OrderDetailsRepo orderDetailsRepo;

    public OrdersServiceImpl(OrdersRepo ordersRepo, CustomerRepo customerRepo, ProductRepo productRepo, OrderDetailsRepo orderDetailsRepo) {
        this.ordersRepo = ordersRepo;
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
        this.orderDetailsRepo = orderDetailsRepo;
    }

    // ✅ Save order + order details (transaction-like)
    @Override
    public OrdersDto saveOrder(OrdersDto dto) {
        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Orders order = new Orders();
        order.setCustomer(customer);
        order.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());
        order.setTotalAmount(dto.getTotalAmount());

        Orders savedOrder = ordersRepo.save(order);

        // ✅ Save order details and update product quantities
        if (dto.getOrderDetails() != null) {
            for (OrderDetailsDto detailDto : dto.getOrderDetails()) {
                Product product = productRepo.findById(detailDto.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                if (product.getQuantity() < detailDto.getQuantity()) {
                    throw new RuntimeException("Insufficient stock for product: " + product.getName());
                }

                // Decrease product quantity
                product.setQuantity(product.getQuantity() - detailDto.getQuantity());
                productRepo.save(product);

                // Save order details
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrder(savedOrder);
                orderDetails.setProduct(product);
                orderDetails.setQuantity(detailDto.getQuantity());
                orderDetails.setAmount(detailDto.getAmount());
                orderDetailsRepo.save(orderDetails);
            }
        }

        return new OrdersDto(
                savedOrder.getId(),
                savedOrder.getCustomer().getId(),
                savedOrder.getDate(),
                savedOrder.getTotalAmount(),
                dto.getOrderDetails()
        );
    }

    // ✅ Update order
    @Override
    public OrdersDto updateOrder(Integer id, OrdersDto dto) {
        Orders order = ordersRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setDate(dto.getDate() != null ? dto.getDate() : order.getDate());
        order.setTotalAmount(dto.getTotalAmount());
        ordersRepo.save(order);

        return new OrdersDto(order.getId(), order.getCustomer().getId(), order.getDate(), order.getTotalAmount(), dto.getOrderDetails());
    }

    // ✅ Delete order
    @Override
    public void deleteOrder(Integer id) {
        Orders order = ordersRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        ordersRepo.delete(order);
    }

    // ✅ Get by ID
    @Override
    public OrdersDto getOrderById(Integer id) {
        Orders order = ordersRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<OrderDetailsDto> details = orderDetailsRepo.findByOrder(order).stream()
                .map(detail -> new OrderDetailsDto(
                        detail.getId(),
                        order.getId(),
                        detail.getProduct().getId(),
                        detail.getQuantity(),
                        detail.getAmount()
                ))
                .collect(Collectors.toList());

        return new OrdersDto(order.getId(), order.getCustomer().getId(), order.getDate(), order.getTotalAmount(), details);
    }

    // ✅ Get all orders
    @Override
    public List<OrdersDto> getAllOrders() {
        return ordersRepo.findAll().stream()
                .map(order -> new OrdersDto(
                        order.getId(),
                        order.getCustomer().getId(),
                        order.getDate(),
                        order.getTotalAmount(),
                        null
                ))
                .collect(Collectors.toList());
    }
}
