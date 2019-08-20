package pl.oczadly.spring.topics.user.control;

import org.junit.jupiter.api.Test;
import pl.oczadly.spring.topics.user.entity.dto.UserRegisterDTO;
import pl.oczadly.spring.topics.user.entity.exception.EmailExistsException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class UserValidationServiceImplementationTest {

/*
    @Test
    public void whenRegisterNewUserWithExistingEmailShouldThrowEmailExistsException() {
        given(userRepository.existsByEmail(USER2_EMAIL)).willReturn(true);
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setEmail(USER2_EMAIL);
        userRegisterDTO.setFirstName(USER2_FIRSTNAME);
        userRegisterDTO.setLastName(USER2_LASTNAME);
        userRegisterDTO.setNickname(USER2_NICKNAME);
        userRegisterDTO.setPassword(USER2_PASSWORD);

        assertThrows(EmailExistsException.class, () -> userService.registerNewUser(userRegisterDTO));
    }
*/

    @Test
    public void whenRegisterNewUserWithExistingNicknameShouldThrowNicknameExistsException() {

    }

    @Test
    public void whenRegisterNewUserWithIncorrectEmailShouldThrowIncorrectEmailException() {

    }
}