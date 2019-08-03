package pl.oczadly.spring.topics.user.boundary;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.oczadly.spring.topics.user.control.UserService;
import pl.oczadly.spring.topics.user.entity.User;
import pl.oczadly.spring.topics.user.entity.UserDTO;

@RestController
@RequestMapping("user")
public class UserRestController {

    private UserService userService;

    @GetMapping(value = "/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping
    public User registerNewUser(@RequestBody UserDTO userDTO) {
        return userService.registerNewUser(userDTO);
    }

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}