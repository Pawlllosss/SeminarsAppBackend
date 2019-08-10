package pl.oczadly.spring.topics.user.control;

import pl.oczadly.spring.topics.user.entity.User;
import pl.oczadly.spring.topics.user.entity.dto.UserRegisterDTO;
import pl.oczadly.spring.topics.user.entity.dto.UserSignUpDTO;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByEmail(String email);

    User registerNewUser(UserRegisterDTO userRegisterDTO);
}
