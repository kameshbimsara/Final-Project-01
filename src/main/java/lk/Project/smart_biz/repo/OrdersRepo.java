package lk.Project.smart_biz.repo;

import lk.Project.smart_biz.entity.Customer;
import lk.Project.smart_biz.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepo extends JpaRepository<Orders, Integer> {
    List<Orders> findByCustomer(Customer customer);
}
