package lk.Project.SmartBiz.repo;

import lk.Project.SmartBiz.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    List<Product> findBySupplierId(Integer supplierId);
}
