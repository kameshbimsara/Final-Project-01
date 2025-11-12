package lk.Project.SmartBiz.repo;

import lk.Project.SmartBiz.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepo extends JpaRepository<Orders, Integer> {
}
