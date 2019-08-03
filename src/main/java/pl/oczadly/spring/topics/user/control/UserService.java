package pl.oczadly.spring.topics.user.control;

import pl.oczadly.spring.topics.user.entity.User;
import pl.oczadly.spring.topics.user.entity.UserDTO;

public interface UserService {

    User getUserById(Long id);

    User getUserByEmail(String email);

    User registerNewUser(UserDTO userDTO);
}