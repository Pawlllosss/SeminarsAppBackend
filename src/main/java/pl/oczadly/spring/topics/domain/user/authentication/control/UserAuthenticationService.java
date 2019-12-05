package pl.oczadly.spring.topics.domain.user.authentication.control;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.oczadly.spring.topics.domain.user.authentication.entity.UserAuthenticationResponseDTO;
import pl.oczadly.spring.topics.domain.user.management.entity.dto.UserSignUpDTO;

public interface UserAuthenticationService extends UserDetailsService {

    UserDetails loadUserById(Long id);

    UserAuthenticationResponseDTO authenticateUser(UserSignUpDTO userSignUpDTO);
}
