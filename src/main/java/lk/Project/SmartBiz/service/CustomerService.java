package lk.Project.SmartBiz.service;

import lk.Project.SmartBiz.dto.CustomerDto;
import java.util.List;

public interface CustomerService {
    CustomerDto saveCustomer(CustomerDto customerDto);
    CustomerDto updateCustomer(Integer id, CustomerDto customerDto);
    void deleteCustomer(Integer id);
    CustomerDto getCustomerById(Integer id);
    List<CustomerDto> getAllCustomers();
}
