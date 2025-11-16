package lk.Project.SmartBiz.repo;

import lk.Project.SmartBiz.entity.BizOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BizOwnerRepo extends JpaRepository<BizOwner, Integer> {
    Optional<BizOwner> findByUsername(String username);

}
