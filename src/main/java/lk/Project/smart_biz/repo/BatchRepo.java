package lk.Project.smart_biz.repo;

import lk.Project.smart_biz.entity.Batch;
import lk.Project.smart_biz.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BatchRepo extends JpaRepository<Batch, Integer> {
    List<Batch> findByProduct(Product product);
}
