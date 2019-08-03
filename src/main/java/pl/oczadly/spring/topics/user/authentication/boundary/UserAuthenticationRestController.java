package pl.oczadly.spring.topics.user.authentication.boundary;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.oczadly.spring.topics.user.authentication.control.UserAuthenticationService;
import pl.oczadly.spring.topics.user.authentication.entity.UserAuthenticationResponseDTO;
import pl.oczadly.spring.topics.user.entity.UserDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("authentication")
public class UserAuthenticationRestController {

    private UserAuthenticationService authenticationService;

    public UserAuthenticationRestController(UserAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signin")
    public UserAuthenticationResponseDTO signIn(@Valid @RequestBody UserDTO userDTO) {
        return authenticationService.authenticateUser(userDTO);
    }

    public UserAuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public void setAuthenticationService(UserAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
}
