package lk.Project.smart_biz.controller;

import lk.Project.smart_biz.dto.CustomerDto;
import lk.Project.smart_biz.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public CustomerDto createCustomer(@RequestBody CustomerDto dto) {
        return customerService.saveCustomer(dto);
    }

    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@PathVariable Integer id, @RequestBody CustomerDto dto) {
        return customerService.updateCustomer(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomer(@PathVariable Integer id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/name/{customerName}")
    public CustomerDto getCustomerByName(@PathVariable String customerName) {
        return customerService.getCustomerByName(customerName);
    }

    @GetMapping("/business/{businessId}")
    public List<CustomerDto> getCustomersByBusiness(@PathVariable ("businessId") Integer businessId) {
        System.out.println("Fetching customers for business ID: " + businessId);
        return customerService.getCustomersByBusiness(businessId);
    }


}
