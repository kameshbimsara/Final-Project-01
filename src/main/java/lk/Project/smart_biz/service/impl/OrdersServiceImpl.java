package lk.Project.smart_biz.service.impl;

import jakarta.transaction.Transactional;
import lk.Project.smart_biz.dto.OrderDetailsDto;
import lk.Project.smart_biz.dto.OrdersDto;
import lk.Project.smart_biz.entity.*;
import lk.Project.smart_biz.repo.*;
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
    private final BatchRepo batchRepo;

    public OrdersServiceImpl(OrdersRepo ordersRepo, CustomerRepo customerRepo, BusinessRepo businessRepo, ProductRepo productRepo, BatchRepo batchRepo) {
        this.ordersRepo = ordersRepo;
        this.customerRepo = customerRepo;
        this.businessRepo = businessRepo;
        this.productRepo = productRepo;
        this.batchRepo = batchRepo;
    }

    @Override
    @Transactional
    public OrdersDto saveOrder(OrdersDto dto) {

        for (OrderDetailsDto detail : dto.getOrderDetails()) {
            Integer availableQty =
                    batchRepo.getAvailableQuantity(detail.getProductId());

            if (availableQty == null || availableQty < detail.getQuantity()) {
                throw new RuntimeException(
                        "Not enough stock for product ID: " + detail.getProductId()
                );
            }
        }

        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Business business = businessRepo.findById(dto.getBusinessId())
                .orElseThrow(() -> new RuntimeException("Business not found"));

        Orders order = new Orders();
        order.setCustomer(customer);
        order.setBusiness(business);
        order.setDate(LocalDate.now());

        reduceBatchStock(dto.getOrderDetails());

        List<OrderDetails> orderDetailsList = new ArrayList<>();
        double totalAmount = 0;

        for (OrderDetailsDto d : dto.getOrderDetails()) {

            Product product = productRepo.findById(d.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderDetails od = new OrderDetails();
            od.setProduct(product);
            od.setQuantity(d.getQuantity());
            od.setUnitPrice(d.getUnitPrice());
            od.setPrice(d.getPrice());
            od.setOrder(order);

            totalAmount += d.getPrice();
            orderDetailsList.add(od);
        }

        order.setOrder_details(orderDetailsList);
        order.setTotalAmount((int) Math.round(totalAmount));

        Orders savedOrder = ordersRepo.save(order);

        return new OrdersDto(
                savedOrder.getId(),
                savedOrder.getDate(),
                savedOrder.getTotalAmount(),
                savedOrder.getBusiness().getId(),
                savedOrder.getCustomer().getId(),
                dto.getOrderDetails()
        );
    }


    private void reduceBatchStock(List<OrderDetailsDto> details) {

        for (OrderDetailsDto detail : details) {

            int remainingQty = detail.getQuantity();

            Product product = productRepo.findById(detail.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            List<Batch> batches =
                    batchRepo.findByProductOrderByExpireDateAsc(product);

            double appliedUnitPrice = 0;

            for (Batch batch : batches) {

                if (remainingQty <= 0) break;
                if (batch.getQuantity() <= 0) continue;

                int deduct = Math.min(batch.getQuantity(), remainingQty);

                appliedUnitPrice = batch.getUnitPrice();

                batch.setQuantity(batch.getQuantity() - deduct);
                remainingQty -= deduct;
            }

            if (remainingQty > 0) {
                throw new RuntimeException(
                        "Insufficient stock for product: " + product.getName()
                );
            }

            detail.setUnitPrice(appliedUnitPrice);
            detail.setPrice(appliedUnitPrice * detail.getQuantity());
        }
    }


    @Override
    @Transactional
    public OrdersDto updateOrder(Integer id, OrdersDto dto) {

        Orders order = ordersRepo.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

        order.setDate(dto.getDate() != null ? dto.getDate() : order.getDate());
        order.setTotalAmount(dto.getTotalAmount());

        order.setCustomer(dto.getCustomerId() != null ? customerRepo.findById(dto.getCustomerId()).orElseThrow(() -> new RuntimeException("Customer not found")) : order.getCustomer());
        order.setBusiness(dto.getBusinessId() != null ? businessRepo.findById(dto.getBusinessId()).orElseThrow(() -> new RuntimeException("Business not found")) : order.getBusiness());

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

        return new OrdersDto(saved.getId(), saved.getDate(), saved.getTotalAmount(), saved.getBusiness().getId(), saved.getCustomer().getId(), dto.getOrderDetails());
    }

    @Override
    public void deleteOrder(Integer id) {
        Orders order = ordersRepo.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        ordersRepo.delete(order);
    }

    @Override
    public OrdersDto getOrderById(Integer id) {
        Orders order = ordersRepo.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

        ArrayList<OrderDetailsDto> orderDetailsDtos = new ArrayList<>();

        for (OrderDetails ent : order.getOrder_details()) {
            OrderDetailsDto dto = new OrderDetailsDto(
                    ent.getId(),
                    ent.getQuantity(),
                    ent.getProduct().getId(),
                    ent.getUnitPrice(),
                    ent.getPrice(),
                    ent.getOrder().getId());
            orderDetailsDtos.add(dto);
        }

        return new OrdersDto(order.getId(), order.getDate(), order.getTotalAmount(), order.getBusiness().getId(), order.getCustomer().getId(), orderDetailsDtos);
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
                        order.getOrder_details().stream()
                                .map(orderDetails -> new OrderDetailsDto(
                                        orderDetails.getId(),
                                        orderDetails.getQuantity(),
                                        orderDetails.getProduct().getId(),
                                        orderDetails.getUnitPrice(),
                                        orderDetails.getPrice(),
                                        orderDetails.getOrder().getId()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdersDto> getOrdersByCustomer(Integer customerId) {
        Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        return ordersRepo.findByCustomer(customer).stream()
                .map(order -> new OrdersDto(
                        order.getId(),
                        order.getDate(),
                        order.getTotalAmount(),
                        order.getBusiness().getId(),
                        order.getCustomer().getId(),
                        order.getOrder_details().stream()
                                .map(orderDetails -> new OrderDetailsDto(
                                        orderDetails.getId(),
                                        orderDetails.getQuantity(),
                                        orderDetails.getProduct().getId(),
                                        orderDetails.getUnitPrice(),
                                        orderDetails.getPrice(),
                                        orderDetails.getId()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdersDto> getOrdersByDate(LocalDate date, Integer businessId) {

        Business business = businessRepo.findById(businessId).orElseThrow(() -> new RuntimeException("Business not found"));

        List<Orders> ordersList = ordersRepo.findByDateAndBusiness(date, business);

        if (ordersList.isEmpty()) {
            throw new RuntimeException("No orders found for date: " + date);
        }

        return ordersList.stream()
                .map(order -> new OrdersDto(
                        order.getId(),
                        order.getDate(),
                        order.getTotalAmount(),
                        order.getBusiness().getId(),
                        order.getCustomer().getId(),
                        order.getOrder_details().stream().
                                map(od -> new OrderDetailsDto(
                                        od.getId(),
                                        od.getQuantity(),
                                        od.getProduct().getId(),
                                        od.getUnitPrice(),
                                        od.getPrice(),
                                        od.getOrder().getId()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdersDto> getOrderByCustomerPhone(String customerPhone, Integer businessId){
        Business business = businessRepo.findById(businessId)
                .orElseThrow(() -> new RuntimeException("Business not found"));

        List<Orders> ordersList = ordersRepo.findByCustomer_PhoneAndBusiness_Id(customerPhone, businessId);
        if (ordersList.isEmpty()) {
            throw new RuntimeException("No orders found for customer phone: " + customerPhone);
        }

        return ordersList.stream()
                .map(order -> new OrdersDto(
                        order.getId(),
                        order.getDate(),
                        order.getTotalAmount(),
                        order.getBusiness().getId(),
                        order.getCustomer().getId(),
                        order.getOrder_details().stream().
                                map(od -> new OrderDetailsDto(
                                        od.getId(),
                                        od.getQuantity(),
                                        od.getProduct().getId(),
                                        od.getUnitPrice(),
                                        od.getPrice(),
                                        od.getOrder().getId()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

    }


}
