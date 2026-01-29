package lk.Project.smart_biz.repo;

import lk.Project.smart_biz.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Integer> {
}
