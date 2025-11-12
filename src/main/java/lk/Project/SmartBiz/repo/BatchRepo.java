package lk.Project.SmartBiz.repo;

import lk.Project.SmartBiz.entity.Batch;
import lk.Project.SmartBiz.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BatchRepo extends JpaRepository<Batch, Integer> {
    List<Batch> findByProduct(Product product);
}
