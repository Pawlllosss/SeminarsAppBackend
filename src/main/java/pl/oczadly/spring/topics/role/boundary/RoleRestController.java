package pl.oczadly.spring.topics.role.boundary;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.oczadly.spring.topics.role.control.RoleService;
import pl.oczadly.spring.topics.role.entity.CourseVoterRole;
import pl.oczadly.spring.topics.role.entity.dto.CourseVoterRoleDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("role")
public class RoleRestController {

    private RoleService roleService;

    private ModelMapper modelMapper;

    public RoleRestController(RoleService roleService, ModelMapper modelMapper) {
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/voter")
    public List<CourseVoterRoleDTO> getAllCourseVoterRoles() {
        List<CourseVoterRole> courseVoterRoles = roleService.getVoterRoles();
        return convertToDTO(courseVoterRoles);
    }

    private List<CourseVoterRoleDTO> convertToDTO(List<CourseVoterRole> courseVoterRoles) {
        return courseVoterRoles.stream()
                .map(voterRole -> modelMapper.map(voterRole, CourseVoterRoleDTO.class))
                .collect(Collectors.toList());
    }
}
