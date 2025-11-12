package lk.Project.SmartBiz.repo;

import lk.Project.SmartBiz.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

}
