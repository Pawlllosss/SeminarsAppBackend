package pl.oczadly.spring.topics.user.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.oczadly.spring.topics.user.control.UserService;
import pl.oczadly.spring.topics.user.entity.User;

@Service
public class UserAuthenticationServiceImplementation implements UserAuthenticationService {

    UserService userService;

    public UserAuthenticationServiceImplementation(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email);

        return UserAuthenticationDetails.builder()
                .fromUser(user)
                .build();
    }

    @Override
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userService.getUserById(id);

        return UserAuthenticationDetails.builder()
                .fromUser(user)
                .build();
    }
}
