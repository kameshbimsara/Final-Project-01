package lk.Project.smart_biz.repo;

import lk.Project.smart_biz.entity.Business;
import lk.Project.smart_biz.entity.Customer;
import lk.Project.smart_biz.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrdersRepo extends JpaRepository<Orders, Integer> {
    List<Orders> findByCustomer(Customer customer);
    List<Orders> findByDateAndBusiness(LocalDate date, Business business);
}
