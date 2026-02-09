package lk.Project.smart_biz.service;

import lk.Project.smart_biz.dto.CustomerDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    CustomerDto saveCustomer(CustomerDto customerDto);

    CustomerDto updateCustomer(Integer id, CustomerDto customerDto);

    void deleteCustomer(Integer id);

    CustomerDto getCustomerById(Integer id);

    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerByName(String name);

    List<CustomerDto> getCustomersByBusiness(Integer businessId);

    ResponseEntity<CustomerDto> getCustomerByPhoneNumber(String phone, Integer businessId);
}
