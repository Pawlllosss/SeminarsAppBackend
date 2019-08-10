package pl.oczadly.spring.topics.user.control;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.oczadly.spring.topics.role.Role;
import pl.oczadly.spring.topics.role.RoleRepository;
import pl.oczadly.spring.topics.user.entity.User;
import pl.oczadly.spring.topics.user.entity.UserDTO;
import pl.oczadly.spring.topics.user.entity.exception.EmailExistsException;
import pl.oczadly.spring.topics.user.repository.UserNotFoundException;
import pl.oczadly.spring.topics.user.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImplementation implements UserService {

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
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findOptionalByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    //TODO: create some response DTO
    @Override
    public User registerNewUser(UserDTO userDTO) {
        String email = userDTO.getEmail();

        if (emailExists(email)) {
            throw new EmailExistsException();
        }

        User user = mapper.map(userDTO, User.class);
        String encodedPassword = encodeUserPassword(user);
        user.setPassword(encodedPassword);

        Role role = retrieveUserRoleForNewUser();
        Set<Role> userRoles = Set.of(role);
        user.setRoles(userRoles);

        return userRepository.save(user);
    }

    private boolean emailExists(String email) {
        return userRepository.findOptionalByEmail(email).isPresent();
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

    public UserRepository getUserRepository() {
        return userRepository;
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
