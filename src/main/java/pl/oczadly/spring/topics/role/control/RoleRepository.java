package pl.oczadly.spring.topics.role.control;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.oczadly.spring.topics.role.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findOptionalByName(String name);
}
