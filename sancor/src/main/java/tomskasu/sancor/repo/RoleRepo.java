package tomskasu.sancor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tomskasu.sancor.entity.Roles;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(String name);
}
