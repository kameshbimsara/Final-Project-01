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

    @PostMapping
    public OrdersDto saveOrder(@RequestBody OrdersDto dto) {
        return ordersService.saveOrder(dto);
    }

    @PutMapping("/{id}")
    public OrdersDto updateOrder(@PathVariable Integer id, @RequestBody OrdersDto dto) {
        return ordersService.updateOrder(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Integer id) {
        ordersService.deleteOrder(id);
    }

    @GetMapping("/{id}")
    public OrdersDto getOrderById(@PathVariable Integer id) {
        return ordersService.getOrderById(id);
    }

    @GetMapping
    public List<OrdersDto> getAllOrders() {
        return ordersService.getAllOrders();
    }
}
