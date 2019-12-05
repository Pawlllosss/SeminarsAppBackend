package pl.oczadly.spring.topics.domain.role.control;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.oczadly.spring.topics.domain.privilege.control.PrivilegeService;
import pl.oczadly.spring.topics.domain.privilege.entity.Privilege;
import pl.oczadly.spring.topics.domain.role.entity.CourseVoterRole;
import pl.oczadly.spring.topics.domain.role.entity.Role;
import pl.oczadly.spring.topics.domain.role.entity.dto.RoleDTO;
import pl.oczadly.spring.topics.domain.role.entity.exception.RoleNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImplementation implements RoleService {

    private RoleRepository roleRepository;
    private CourseVoterRoleRepository voterRoleRepository;

    private PrivilegeService privilegeService;

    private ModelMapper modelMapper;

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        List<RoleDTO> roleDTOs = roles.stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .collect(Collectors.toList());

        return roleDTOs;
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id));
    }

    @Override
    public List<CourseVoterRole> getVoterRoles() {
        return voterRoleRepository.findAll();
    }

    @Override
    public CourseVoterRole createCourseVoterRole(String courseName) {
        Privilege privilege = createAndPersistVoterPrivilege(courseName);
        Role role = createAndPersistVoterRole(courseName, privilege);

        CourseVoterRole courseVoterRole = new CourseVoterRole(role);
        CourseVoterRole persistedCourseVoterRole = voterRoleRepository.save(courseVoterRole);

        return persistedCourseVoterRole;
    }

    private Privilege createAndPersistVoterPrivilege(String courseName) {
        String voterPrivilegeName = getVoterPrivilegeName(courseName);
        return privilegeService.createPrivilege(voterPrivilegeName);
    }

    private String getVoterPrivilegeName(String courseName) {
        return "VOTE_" + courseName;
    }

    private Role createAndPersistVoterRole(String courseName, Privilege privilege) {
        String voterRoleName = getVoterRoleName(courseName);
        Role role = new Role(voterRoleName, Set.of(privilege));
        return roleRepository.save(role);
    }

    private String getVoterRoleName(String courseName) {
        return "VOTER_" + courseName;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setVoterRoleRepository(CourseVoterRoleRepository voterRoleRepository) {
        this.voterRoleRepository = voterRoleRepository;
    }

    @Autowired
    public void setPrivilegeService(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
