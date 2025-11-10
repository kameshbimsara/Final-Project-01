package lk.Project.SmartBiz.repo;

import lk.Project.SmartBiz.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrdersRepo extends JpaRepository<Orders, Integer> {
    List<Orders> findByCustomerId(Integer customerId);
}
