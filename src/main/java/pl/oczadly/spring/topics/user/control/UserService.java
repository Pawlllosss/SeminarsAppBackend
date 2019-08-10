package pl.oczadly.spring.topics.user.control;

import pl.oczadly.spring.topics.user.entity.User;
import pl.oczadly.spring.topics.user.entity.UserCredentialsDTO;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByEmail(String email);

    User registerNewUser(UserCredentialsDTO userCredentialsDTO);
}
