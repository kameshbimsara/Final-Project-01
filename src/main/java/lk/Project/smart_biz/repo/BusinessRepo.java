package lk.Project.smart_biz.repo;

import lk.Project.smart_biz.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessRepo extends JpaRepository<Business, Integer> {
    Optional<Business> findByUsername(String username);
}