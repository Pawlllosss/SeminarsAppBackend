package pl.oczadly.spring.topics.user.authentication.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.oczadly.spring.topics.user.authentication.boundary.annotation.CurrentUser;
import pl.oczadly.spring.topics.user.authentication.control.UserAuthenticationService;
import pl.oczadly.spring.topics.user.authentication.entity.UserAuthenticationDetails;
import pl.oczadly.spring.topics.user.authentication.entity.UserAuthenticationResponseDTO;
import pl.oczadly.spring.topics.user.management.boundary.UserResourceAssembler;
import pl.oczadly.spring.topics.user.management.control.UserService;
import pl.oczadly.spring.topics.user.management.entity.User;
import pl.oczadly.spring.topics.user.management.entity.dto.UserResponseDTO;
import pl.oczadly.spring.topics.user.management.entity.dto.UserSignUpDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("authentication")
public class UserAuthenticationRestController {

    private UserAuthenticationService authenticationService;
    private UserService userService;

    private UserResourceAssembler userResourceAssembler;

    public UserAuthenticationRestController(UserAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signin")
    public UserAuthenticationResponseDTO signIn(@Valid @RequestBody UserSignUpDTO userSignUpDTO) {
        return authenticationService.authenticateUser(userSignUpDTO);
    }

    @GetMapping(value = "/current", produces = { "application/hal+json" })
    public Resource<UserResponseDTO> getCurrentUser(@CurrentUser UserAuthenticationDetails currentUserAuthenticationDetails) {
        //TODO: handle case when user details are no longer valid
        Long id = currentUserAuthenticationDetails.getId();
        User user = userService.getUserById(id);
        return userResourceAssembler.toResource(user);
    }


    public UserAuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    @Autowired
    public void setAuthenticationService(UserAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
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
