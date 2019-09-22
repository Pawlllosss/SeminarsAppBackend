package pl.oczadly.spring.topics.role.control;

import pl.oczadly.spring.topics.role.entity.CourseVoterRole;
import pl.oczadly.spring.topics.role.entity.RoleDTO;

import java.util.List;

public interface RoleService {

    List<RoleDTO> getAllRoles();

    List<CourseVoterRole> getVoterRoles();

    CourseVoterRole createCourseVoterRole(String courseName);
}
