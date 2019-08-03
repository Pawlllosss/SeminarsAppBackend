package pl.oczadly.spring.topics.user.control;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.oczadly.spring.topics.role.Role;
import pl.oczadly.spring.topics.role.RoleRepository;
import pl.oczadly.spring.topics.user.entity.User;
import pl.oczadly.spring.topics.user.entity.UserDTO;
import pl.oczadly.spring.topics.user.entity.exception.EmailExistsException;
import pl.oczadly.spring.topics.user.repository.UserNotFoundException;
import pl.oczadly.spring.topics.user.repository.UserRepository;

import java.util.Set;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private ModelMapper mapper;

    //TODO: move it to setter?
    public UserServiceImplementation(UserRepository userRepository, RoleRepository roleRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findOptionalByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User registerNewUser(UserDTO userDTO) {
        String email = userDTO.getEmail();

        if (emailExists(email)) {
            throw new EmailExistsException();
        }

        User user = mapper.map(userDTO, User.class);
        Role role = retrieveUserRoleForNewUser();
        Set<Role> userRoles = Set.of(role);
        user.setRoles(userRoles);

        return userRepository.save(user);
    }

    private Role retrieveUserRoleForNewUser() {
        return roleRepository.findOptionalByName("USER")
                .orElseThrow(() -> new IllegalStateException("Error during creating a user"));
    }

    private boolean emailExists(String email) {
        return userRepository.findOptionalByEmail(email).isPresent();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
