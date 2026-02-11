package lk.Project.smart_biz.controller;

import lk.Project.smart_biz.dto.DateReqDto;
import lk.Project.smart_biz.dto.OrderCustomerReqDto;
import lk.Project.smart_biz.dto.OrdersDto;
import lk.Project.smart_biz.service.OrdersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/customer/{customerId}")
    public List<OrdersDto> getOrdersByCustomer(@PathVariable Integer customerId) {
        return ordersService.getOrdersByCustomer(customerId);
    }

    @PostMapping("/orderDate")
    public List<OrdersDto> getOrdersByDate(@RequestBody DateReqDto dateReqDto) {
        return ordersService.getOrdersByDate(dateReqDto.getDate(), dateReqDto.getBusinessId());
    }

    @PostMapping("/customerPhone")
    public List<OrdersDto> getOrderByCustomerPhone(@RequestBody OrderCustomerReqDto orderCustomerReqDto) {
        return ordersService.getOrderByCustomerPhone(orderCustomerReqDto.getCustomerPhone(),orderCustomerReqDto.getBusinessId());
    }

}
