package lk.Project.SmartBiz.controller;

import lk.Project.SmartBiz.dto.OrdersDto;
import lk.Project.SmartBiz.service.OrdersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin
public class OrdersController {

    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    // ✅ Save a new order (with order details)
    @PostMapping
    public OrdersDto saveOrder(@RequestBody OrdersDto dto) {
        return ordersService.saveOrder(dto);
    }

    // ✅ Update existing order
    @PutMapping("/{id}")
    public OrdersDto updateOrder(@PathVariable Integer id, @RequestBody OrdersDto dto) {
        return ordersService.updateOrder(id, dto);
    }

    // ✅ Delete order by ID
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Integer id) {
        ordersService.deleteOrder(id);
    }

    // ✅ Get order by ID
    @GetMapping("/{id}")
    public OrdersDto getOrderById(@PathVariable Integer id) {
        return ordersService.getOrderById(id);
    }

    // ✅ Get all orders
    @GetMapping
    public List<OrdersDto> getAllOrders() {
        return ordersService.getAllOrders();
    }
}
