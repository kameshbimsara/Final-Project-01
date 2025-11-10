package lk.Project.SmartBiz.controller;

import lk.Project.SmartBiz.dto.OrderDetailsDto;
import lk.Project.SmartBiz.service.OrderDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-details")
@CrossOrigin
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;

    public OrderDetailsController(OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }

    @PostMapping
    public OrderDetailsDto createOrderDetail(@RequestBody OrderDetailsDto dto) {
        return orderDetailsService.saveOrderDetail(dto);
    }

    @PutMapping("/{id}")
    public OrderDetailsDto updateOrderDetail(@PathVariable Integer id, @RequestBody OrderDetailsDto dto) {
        return orderDetailsService.updateOrderDetail(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderDetail(@PathVariable Integer id) {
        orderDetailsService.deleteOrderDetail(id);
    }

    @GetMapping("/{id}")
    public OrderDetailsDto getOrderDetailById(@PathVariable Integer id) {
        return orderDetailsService.getOrderDetailById(id);
    }

    @GetMapping
    public List<OrderDetailsDto> getAllOrderDetails() {
        return orderDetailsService.getAllOrderDetails();
    }

    @GetMapping("/order/{orderId}")
    public List<OrderDetailsDto> getOrderDetailsByOrder(@PathVariable Integer orderId) {
        return orderDetailsService.getOrderDetailsByOrder(orderId);
    }
}
