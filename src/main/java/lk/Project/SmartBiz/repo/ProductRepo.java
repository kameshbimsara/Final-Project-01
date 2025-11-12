package lk.Project.SmartBiz.repo;

import lk.Project.SmartBiz.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}
