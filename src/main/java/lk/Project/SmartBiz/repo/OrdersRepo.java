package lk.Project.SmartBiz.repo;

import lk.Project.SmartBiz.entity.Customer;
import lk.Project.SmartBiz.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdersRepo extends JpaRepository<Orders, Integer> {
    List<Orders> findByCustomer(Customer customer);
}
