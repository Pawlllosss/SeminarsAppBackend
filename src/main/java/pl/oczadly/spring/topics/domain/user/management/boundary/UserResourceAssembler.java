package pl.oczadly.spring.topics.domain.user.management.boundary;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.oczadly.spring.topics.domain.privilege.entity.PrivilegeDTO;
import pl.oczadly.spring.topics.domain.role.entity.Role;
import pl.oczadly.spring.topics.domain.role.entity.dto.RoleDTO;
import pl.oczadly.spring.topics.domain.seminar.boundary.SeminarDetailResponseDTOMapper;
import pl.oczadly.spring.topics.domain.seminar.entity.Seminar;
import pl.oczadly.spring.topics.domain.seminar.entity.dto.SeminarDetailResponseDTO;
import pl.oczadly.spring.topics.domain.user.management.entity.User;
import pl.oczadly.spring.topics.domain.user.management.entity.dto.UserResponseDTO;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class UserResourceAssembler implements ResourceAssembler<User, Resource<UserResponseDTO>> {

    private ModelMapper modelMapper;

    private SeminarDetailResponseDTOMapper seminarDetailResponseDTOMapper;

    public UserResourceAssembler(ModelMapper modelMapper, SeminarDetailResponseDTOMapper seminarDetailResponseDTOMapper) {
        this.modelMapper = modelMapper;
        this.seminarDetailResponseDTOMapper = seminarDetailResponseDTOMapper;
    }

    @Override
    public Resource<UserResponseDTO> toResource(User user) {
        Set<Role> roles = user.getRoles();
        Set<RoleDTO> roleDTOs = roles.stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .collect(Collectors.toSet());
        Set<PrivilegeDTO> privilegeDTOs = roles.stream()
                .map(Role::getPrivileges)
                .flatMap(Collection::stream)
                .map(privilege -> modelMapper.map(privilege, PrivilegeDTO.class))
                .collect(Collectors.toSet());
        List<Seminar> seminars = user.getSeminars();
        List<SeminarDetailResponseDTO> seminarsDetailResponseDTO = convertSeminarsToSeminarDetailResponseDTOs(seminars);

        UserResponseDTO userResponseDTO = modelMapper.map(user, UserResponseDTO.class);
        userResponseDTO.setRoles(roleDTOs);
        userResponseDTO.setPrivileges(privilegeDTOs);
        userResponseDTO.setSeminars(seminarsDetailResponseDTO);

        Long userId = user.getId();
        Link selfLink = linkTo(methodOn(UserRestController.class).getUserById(userId))
                .withSelfRel();
        return new Resource<>(userResponseDTO, selfLink);
    }

    private List<SeminarDetailResponseDTO> convertSeminarsToSeminarDetailResponseDTOs(List<Seminar> seminars) {
        return seminars.stream()
            .map(seminarDetailResponseDTOMapper::convertSeminarToSeminarDetailResponseDTO)
            .collect(Collectors.toList());
    }

}
