package pl.oczadly.spring.topics.domain.user.management.control;

import pl.oczadly.spring.topics.domain.seminar.entity.Seminar;
import pl.oczadly.spring.topics.domain.user.management.entity.User;
import pl.oczadly.spring.topics.domain.user.management.entity.dto.UserRegisterDTO;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByEmail(String email);

    User registerNewUser(UserRegisterDTO userRegisterDTO);

    User updateRoles(Set<Long> rolesId, Long id);

    User assignSeminarToUser(User user, Seminar seminar);
}
