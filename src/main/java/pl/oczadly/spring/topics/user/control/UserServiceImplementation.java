package pl.oczadly.spring.topics.user.control;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.oczadly.spring.topics.user.entity.User;
import pl.oczadly.spring.topics.user.entity.UserCreateDTO;
import pl.oczadly.spring.topics.user.entity.exception.EmailExistsException;
import pl.oczadly.spring.topics.user.repository.UserNotFoundException;
import pl.oczadly.spring.topics.user.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;

    private ModelMapper mapper;

    public UserServiceImplementation(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findOptionalByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User registerNewUser(UserCreateDTO userCreateDTO) {
        String email = userCreateDTO.getEmail();

        if (emailExists(email)) {
            throw new EmailExistsException();
        }

        User user = mapper.map(userCreateDTO, User.class);

        return userRepository.save(user);
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
