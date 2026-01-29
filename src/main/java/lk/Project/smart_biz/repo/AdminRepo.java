package lk.Project.smart_biz.repo;

import lk.Project.smart_biz.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByUsername(String username);
}
