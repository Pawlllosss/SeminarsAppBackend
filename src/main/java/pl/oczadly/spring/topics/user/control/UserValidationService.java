package pl.oczadly.spring.topics.user.control;

import pl.oczadly.spring.topics.user.entity.dto.UserRegisterDTO;

public interface UserValidationService {

    void validateUserRegisterDTO(UserRegisterDTO userRegisterDTO);
}
