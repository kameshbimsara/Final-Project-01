package lk.Project.SmartBiz.repo;

import lk.Project.SmartBiz.entity.OrderDetails;
import lk.Project.SmartBiz.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Integer> {
    List<OrderDetails> findByOrderId(Integer orderId);
    List<OrderDetails> findByOrder(Orders order);
}
