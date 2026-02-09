package lk.Project.smart_biz.repo;

import lk.Project.smart_biz.entity.Customer;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    Optional <Customer> findByName(String name);
    List<Customer> findByBusiness_Id(Integer businessId);
    Optional<Customer> findByPhoneAndBusiness_Id(String phone, Integer businessId);
}
