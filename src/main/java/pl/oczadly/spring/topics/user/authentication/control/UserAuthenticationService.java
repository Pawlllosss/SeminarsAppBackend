package pl.oczadly.spring.topics.user.authentication.control;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.oczadly.spring.topics.user.authentication.entity.UserAuthenticationResponseDTO;
import pl.oczadly.spring.topics.user.entity.UserDTO;

public interface UserAuthenticationService extends UserDetailsService {

    UserDetails loadUserById(Long id);

    UserAuthenticationResponseDTO authenticateUser(UserDTO userDTO);
}