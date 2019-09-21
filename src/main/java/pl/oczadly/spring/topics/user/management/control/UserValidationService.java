package pl.oczadly.spring.topics.user.management.control;

import pl.oczadly.spring.topics.user.management.entity.dto.UserRegisterDTO;

public interface UserValidationService {

    void validateUserRegisterDTO(UserRegisterDTO userRegisterDTO);
}
