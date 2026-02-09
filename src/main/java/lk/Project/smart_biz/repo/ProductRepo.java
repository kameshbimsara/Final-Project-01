package lk.Project.smart_biz.repo;

import lk.Project.smart_biz.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    Optional<Product> findByNameAndBusiness_Id(String name, Integer businessId);
}
