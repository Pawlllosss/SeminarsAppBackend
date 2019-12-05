package pl.oczadly.spring.topics.domain.role.control;

import pl.oczadly.spring.topics.domain.role.entity.CourseVoterRole;
import pl.oczadly.spring.topics.domain.role.entity.Role;
import pl.oczadly.spring.topics.domain.role.entity.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    List<RoleDTO> getAllRoles();

    Role getRoleById(Long id);

    List<CourseVoterRole> getVoterRoles();

    CourseVoterRole createCourseVoterRole(String courseName);
}
