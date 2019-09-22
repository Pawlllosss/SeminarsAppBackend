package pl.oczadly.spring.topics.role.control;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.oczadly.spring.topics.role.entity.CourseVoterRole;

public interface CourseVoterRoleRepository extends JpaRepository<CourseVoterRole, Long> {
}
