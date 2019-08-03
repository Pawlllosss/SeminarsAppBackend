package pl.oczadly.spring.topics.user.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAuthenticationService extends UserDetailsService {
    UserDetails loadUserById(Long id);
}
