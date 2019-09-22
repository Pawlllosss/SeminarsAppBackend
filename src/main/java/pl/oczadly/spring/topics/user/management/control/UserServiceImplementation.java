package pl.oczadly.spring.topics.user.management.control;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.oczadly.spring.topics.role.entity.Role;
import pl.oczadly.spring.topics.role.control.RoleRepository;
import pl.oczadly.spring.topics.user.management.entity.User;
import pl.oczadly.spring.topics.user.management.entity.dto.UserRegisterDTO;
import pl.oczadly.spring.topics.user.management.entity.exception.UserNotFoundException;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImplementation implements UserService {

    private UserValidationService userValidationService;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;
    private ModelMapper mapper;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findOptionalByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public User registerNewUser(UserRegisterDTO userRegisterDTO) {
        userValidationService.validateUserRegisterDTO(userRegisterDTO);

        User user = mapper.map(userRegisterDTO, User.class);
        String encodedPassword = encodeUserPassword(user);
        user.setPassword(encodedPassword);

        Role role = retrieveUserRoleForNewUser();
        Set<Role> userRoles = Set.of(role);
        user.setRoles(userRoles);

        return userRepository.save(user);
    }

    private String encodeUserPassword(User user) {
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);

        return encodedPassword;
    }

    private Role retrieveUserRoleForNewUser() {
        return roleRepository.findOptionalByName("USER")
                .orElseThrow(() -> new IllegalStateException("Error during creating a user"));
    }

    @Autowired
    public void setUserValidationService(UserValidationService userValidationService) {
        this.userValidationService = userValidationService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }
}
