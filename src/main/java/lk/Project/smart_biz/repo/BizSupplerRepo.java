package lk.Project.smart_biz.repo;

import lk.Project.smart_biz.entity.BizSuppler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BizSupplerRepo extends JpaRepository<BizSuppler, Integer> {
    List<BizSuppler> findByBusiness_Id(Integer businessId);
    Optional<BizSuppler> findByContactNoAndBusiness_Id(String contactNo, Integer businessId);
}
