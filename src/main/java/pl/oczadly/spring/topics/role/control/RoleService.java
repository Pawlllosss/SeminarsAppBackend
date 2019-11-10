package pl.oczadly.spring.topics.role.control;

import pl.oczadly.spring.topics.role.entity.CourseVoterRole;
import pl.oczadly.spring.topics.role.entity.Role;
import pl.oczadly.spring.topics.role.entity.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    List<RoleDTO> getAllRoles();

    Role getRoleById(Long id);

    List<CourseVoterRole> getVoterRoles();

    CourseVoterRole createCourseVoterRole(String courseName);
}
