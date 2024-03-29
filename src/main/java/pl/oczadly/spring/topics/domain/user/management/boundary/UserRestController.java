package pl.oczadly.spring.topics.domain.user.management.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.oczadly.spring.topics.domain.user.management.control.UserService;
import pl.oczadly.spring.topics.domain.user.management.entity.User;
import pl.oczadly.spring.topics.domain.user.management.entity.dto.UserRegisterDTO;
import pl.oczadly.spring.topics.domain.user.management.entity.dto.UserResponseDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "user", produces = { "application/hal+json" })
public class UserRestController {

    private UserService userService;

    private UserResourceAssembler userResourceAssembler;

    @GetMapping
    @PreAuthorize("hasAuthority('CRUD_ALL_USERS')")
    public Resources<Resource<UserResponseDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<Resource<UserResponseDTO>> usersResponse = users.stream()
                .map(userResourceAssembler::toResource)
                .collect(Collectors.toList());

        Link selfLink = linkTo(UserRestController.class).withSelfRel();
        return new Resources<>(usersResponse, selfLink);
    }

    @GetMapping(value = "/{id}", produces = { "application/hal+json"})
    public Resource<UserResponseDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return userResourceAssembler.toResource(user);
    }

    @PostMapping
    public Resource<UserResponseDTO> registerNewUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        User user = userService.registerNewUser(userRegisterDTO);
        return userResourceAssembler.toResource(user);
    }

    @PutMapping(value = "/role/{id}", produces = { "application/hal+json"})
    @PreAuthorize("hasAuthority('ROLE_MAINTAINER')")
    public Resource<UserResponseDTO> updateUserRoles(@RequestBody Set<Long> rolesId, @PathVariable Long id) {
        User user = userService.updateRoles(rolesId, id);
        return userResourceAssembler.toResource(user);
    }

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    public UserService getUserService() {
        return userService;
    }

    public UserResourceAssembler getUserResourceAssembler() {
        return userResourceAssembler;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserResourceAssembler(UserResourceAssembler userResourceAssembler) {
        this.userResourceAssembler = userResourceAssembler;
    }
}
