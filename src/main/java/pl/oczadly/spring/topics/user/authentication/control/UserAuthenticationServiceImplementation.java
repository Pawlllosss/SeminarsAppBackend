package pl.oczadly.spring.topics.user.authentication.control;

import org.springframework.beans.factory.annotation.Autowired;
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
import pl.oczadly.spring.topics.user.authentication.control.token.JWTTokenProvider;
import pl.oczadly.spring.topics.user.management.control.UserService;
import pl.oczadly.spring.topics.user.management.entity.User;
import pl.oczadly.spring.topics.user.management.entity.dto.UserSignUpDTO;

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
    public UserAuthenticationResponseDTO authenticateUser(UserSignUpDTO userSignUpDTO) {
        Authentication authentication = createAuthentication(userSignUpDTO);
        setAuthenticationContext(authentication);

        String token = tokenProvider.generateToken(authentication);
        UserAuthenticationResponseDTO responseDTO = new UserAuthenticationResponseDTO(token);

        return responseDTO;
    }

    private Authentication createAuthentication(UserSignUpDTO userSignUpDTO) {
        String email = userSignUpDTO.getEmail();
        String password = userSignUpDTO.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        return authentication;
    }

    private void setAuthenticationContext(Authentication authentication) {
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setTokenProvider(JWTTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
