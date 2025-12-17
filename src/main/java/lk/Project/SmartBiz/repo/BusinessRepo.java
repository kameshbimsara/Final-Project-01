package lk.Project.SmartBiz.repo;

import lk.Project.SmartBiz.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessRepo extends JpaRepository<Business, Integer> {
    List<Business> findByOwnerId(Integer ownerId);

}
