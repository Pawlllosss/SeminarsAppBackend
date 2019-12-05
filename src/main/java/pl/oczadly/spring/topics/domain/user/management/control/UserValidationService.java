package pl.oczadly.spring.topics.domain.user.management.control;

import pl.oczadly.spring.topics.domain.user.management.entity.dto.UserRegisterDTO;

public interface UserValidationService {

    void validateUserRegisterDTO(UserRegisterDTO userRegisterDTO);
}
