package pl.oczadly.spring.topics.user.management.boundary;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.oczadly.spring.topics.privilege.entity.PrivilegeDTO;
import pl.oczadly.spring.topics.role.entity.Role;
import pl.oczadly.spring.topics.role.entity.RoleDTO;
import pl.oczadly.spring.topics.user.management.entity.User;
import pl.oczadly.spring.topics.user.management.entity.dto.UserResponseDTO;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class UserResourceAssembler implements ResourceAssembler<User, Resource<UserResponseDTO>> {

    private ModelMapper modelMapper;

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


        Long userId = user.getId();
        UserResponseDTO userResponseDTO = modelMapper.map(user, UserResponseDTO.class);
        userResponseDTO.setRoles(roleDTOs);
        userResponseDTO.setPrivileges(privilegeDTOs);

        Link selfLink = linkTo(methodOn(UserRestController.class).getUserById(userId))
                .withSelfRel();
        return new Resource<>(userResponseDTO, selfLink);
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
