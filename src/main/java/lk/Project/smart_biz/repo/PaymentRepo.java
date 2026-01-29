package lk.Project.smart_biz.repo;

import lk.Project.smart_biz.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepo extends JpaRepository<Payments, Integer> {
    List<Payments> findByBusiness_Id(Integer businessId);
}
