package pl.oczadly.spring.topics.user.management.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.oczadly.spring.topics.user.management.entity.dto.UserRegisterDTO;
import pl.oczadly.spring.topics.user.management.entity.exception.EmailExistsException;
import pl.oczadly.spring.topics.user.management.entity.exception.NicknameExistsException;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

@Service
public class UserValidationServiceImplementation implements UserValidationService {

    private UserRepository userRepository;

    private Set<Consumer<UserRegisterDTO>> validations;

    public UserValidationServiceImplementation() {
        validations = new HashSet<>();
        validations.add(this::validateIfEmailIsNotUsed);
        validations.add(this::validateIfNicknameIsNotUsed);
    }

    @Override
    public void validateUserRegisterDTO(UserRegisterDTO userRegisterDTO) {
        for(Consumer<UserRegisterDTO> validation : validations) {
            validation.accept(userRegisterDTO);
        }
    }

    private void validateIfEmailIsNotUsed(UserRegisterDTO userRegisterDTO) {
        String email = userRegisterDTO.getEmail();

        if(emailExists(email)) {
            throw new EmailExistsException(email);
        }
    }
    private boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    private void validateIfNicknameIsNotUsed(UserRegisterDTO userRegisterDTO) {
        String nickname = userRegisterDTO.getNickname();

        if(nicknameExists(nickname)) {
            throw new NicknameExistsException(nickname);
        }
    }

    private boolean nicknameExists(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
