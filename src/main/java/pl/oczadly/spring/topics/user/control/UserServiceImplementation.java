package pl.oczadly.spring.topics.user.control;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.oczadly.spring.topics.role.Role;
import pl.oczadly.spring.topics.role.RoleRepository;
import pl.oczadly.spring.topics.user.entity.User;
import pl.oczadly.spring.topics.user.entity.dto.UserRegisterDTO;
import pl.oczadly.spring.topics.user.entity.exception.EmailExistsException;
import pl.oczadly.spring.topics.user.entity.exception.NickNameExistsException;
import pl.oczadly.spring.topics.user.entity.exception.UserNotFoundException;
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
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findOptionalByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    //TODO: throw incorrect email exception
    @Override
    public User registerNewUser(UserRegisterDTO userRegisterDTO) {
        String email = userRegisterDTO.getEmail();

        if(emailExists(email)) {
            throw new EmailExistsException(email);
        }

        String nickName = userRegisterDTO.getNickName();

        if(nickNameExists(nickName)) {
            throw new NickNameExistsException(nickName);
        }

        User user = mapper.map(userRegisterDTO, User.class);
        String encodedPassword = encodeUserPassword(user);
        user.setPassword(encodedPassword);

        Role role = retrieveUserRoleForNewUser();
        Set<Role> userRoles = Set.of(role);
        user.setRoles(userRoles);

        return userRepository.save(user);
    }

    private boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean nickNameExists(String nickName) {
        return userRepository.existsByNickName(nickName);
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
