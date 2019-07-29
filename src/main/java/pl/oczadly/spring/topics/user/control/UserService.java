package pl.oczadly.spring.topics.user.control;

import pl.oczadly.spring.topics.user.entity.User;
import pl.oczadly.spring.topics.user.entity.UserCreateDTO;

public interface UserService {

    User getUserByEmail(String email);

    User registerNewUser(UserCreateDTO userCreateDTO);
}
