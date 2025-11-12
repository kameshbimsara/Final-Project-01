package lk.Project.SmartBiz.service.impl;

import lk.Project.SmartBiz.dto.CustomerDto;
import lk.Project.SmartBiz.entity.Business;
import lk.Project.SmartBiz.entity.Customer;
import lk.Project.SmartBiz.repo.BusinessRepo;
import lk.Project.SmartBiz.repo.CustomerRepo;
import lk.Project.SmartBiz.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private final BusinessRepo businessRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo, BusinessRepo businessRepo) {
        this.customerRepo = customerRepo;
        this.businessRepo = businessRepo;
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto dto) {
        Business business = businessRepo.findById(dto.getBusinessId())
                .orElseThrow(() -> new RuntimeException("Business not found"));

        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setPhone(dto.getPhone());
        customer.setBusiness(business);

        Customer saved = customerRepo.save(customer);
        dto.setId(saved.getId());
        return dto;
    }

    @Override
    public CustomerDto updateCustomer(Integer id, CustomerDto dto) {
        Customer existing = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        existing.setName(dto.getName());
        existing.setPhone(dto.getPhone());

        if (dto.getBusinessId() != null && !dto.getBusinessId().equals(existing.getBusiness().getId())) {
            Business business = businessRepo.findById(dto.getBusinessId())
                    .orElseThrow(() -> new RuntimeException("Business not found"));
            existing.setBusiness(business);
        }

        Customer updated = customerRepo.save(existing);
        return toDto(updated);
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerRepo.deleteById(id);
    }

    @Override
    public CustomerDto getCustomerById(Integer id) {
        Customer c = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return toDto(c);
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    private CustomerDto toDto(Customer c) {
        CustomerDto dto = new CustomerDto();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setPhone(c.getPhone());
        dto.setBusinessId(c.getBusiness().getId());
        return dto;
    }
}
