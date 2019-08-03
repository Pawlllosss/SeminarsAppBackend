package pl.oczadly.spring.topics.user.authentication.control;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.oczadly.spring.topics.user.authentication.entity.UserAuthenticationDetails;
import pl.oczadly.spring.topics.user.authentication.entity.UserAuthenticationResponseDTO;
import pl.oczadly.spring.topics.user.authentication.token.JWTTokenProvider;
import pl.oczadly.spring.topics.user.control.UserService;
import pl.oczadly.spring.topics.user.entity.User;
import pl.oczadly.spring.topics.user.entity.UserDTO;

@Service
public class UserAuthenticationServiceImplementation implements UserAuthenticationService {

    private AuthenticationManager authenticationManager;
    private JWTTokenProvider tokenProvider;
    private UserService userService;

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

    @Override
    public UserAuthenticationResponseDTO authenticateUser(UserDTO userDTO) {
        Authentication authentication = createAuthentication(userDTO);
        setAuthenticationContext(authentication);

        String token = tokenProvider.generateToken(authentication);
        UserAuthenticationResponseDTO responseDTO = new UserAuthenticationResponseDTO(token);

        return responseDTO;
    }

    private Authentication createAuthentication(UserDTO userDTO) {
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        return authentication;
    }

    private void setAuthenticationContext(Authentication authentication) {
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setTokenProvider(JWTTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
