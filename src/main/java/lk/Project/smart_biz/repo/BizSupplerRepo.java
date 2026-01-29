package lk.Project.smart_biz.repo;

import lk.Project.smart_biz.entity.BizSuppler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BizSupplerRepo extends JpaRepository<BizSuppler, Integer> {
    Optional<BizSuppler> findByCompanyName(String companyName);
}
