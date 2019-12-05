package pl.oczadly.spring.topics.domain.role.control;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.oczadly.spring.topics.domain.role.entity.CourseVoterRole;

public interface CourseVoterRoleRepository extends JpaRepository<CourseVoterRole, Long> {
}
