package pl.oczadly.spring.topics.privilege;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Optional<Privilege> findOptionalByName(String name);
}
