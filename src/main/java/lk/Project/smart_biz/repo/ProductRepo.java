package lk.Project.smart_biz.repo;

import lk.Project.smart_biz.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}
