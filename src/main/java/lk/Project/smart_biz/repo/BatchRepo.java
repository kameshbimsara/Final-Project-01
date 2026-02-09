package lk.Project.smart_biz.repo;

import lk.Project.smart_biz.entity.Batch;
import lk.Project.smart_biz.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BatchRepo extends JpaRepository<Batch, Integer> {
    List<Batch> findByProduct(Product product);

    @Query("""
        SELECT SUM(b.quantity)
        FROM Batch b
        WHERE b.product.id = :productId
        AND b.quantity > 0
    """)
    Integer getAvailableQuantity(@Param("productId") Integer productId);

    List<Batch> findByProductOrderByExpireDateAsc(Product product);

}
