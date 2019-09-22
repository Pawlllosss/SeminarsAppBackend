package pl.oczadly.spring.topics.privilege.control;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.oczadly.spring.topics.privilege.entity.Privilege;

import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Optional<Privilege> findOptionalByName(String name);
}
