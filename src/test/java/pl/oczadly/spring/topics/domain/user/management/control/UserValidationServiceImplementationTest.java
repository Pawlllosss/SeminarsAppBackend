package pl.oczadly.spring.topics.domain.user.management.control;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.oczadly.spring.topics.domain.user.management.entity.dto.UserRegisterDTO;
import pl.oczadly.spring.topics.domain.user.management.entity.exception.EmailExistsException;
import pl.oczadly.spring.topics.domain.user.management.entity.exception.NicknameExistsException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class UserValidationServiceImplementationTest {

    private static final String NICKNAME = "Faplowicz";
    private static final String EMAIL = "email@email.com";
    private static final String FIRSTNAME = "Stefan";
    private static final String LASTNAME = "Sztosłowicz";
    private static final String PASSWORD = "bla4Bla!";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserValidationServiceImplementation userValidationService;

    @Test
    public void whenRegisterNewUserWithHappyPathScenarioShouldPassTest() {
        mockExistsByNicknameToReturn(NICKNAME, false);
        mockExistsByEmailToReturn(EMAIL, false);
        UserRegisterDTO userRegisterDTO = createTestUserRegisterDTO();

        userValidationService.validateUserRegisterDTO(userRegisterDTO);

        verifyUserRepositoryExistsByNicknameCalledOnce(NICKNAME);
        verifyUserRepositoryExistsByEmailCalledOnce(EMAIL);
    }

    private void mockExistsByNicknameToReturn(String nickname, Boolean returnValue) {
        given(userRepository.existsByNickname(nickname)).willReturn(returnValue);
    }

    private void mockExistsByEmailToReturn(String email, Boolean returnValue) {
        given(userRepository.existsByEmail(email)).willReturn(returnValue);
    }

    @Test
    public void whenRegisterNewUserWithExistingNicknameShouldThrowNicknameExistsException() {
        mockExistsByNicknameToReturn(NICKNAME, true);
        UserRegisterDTO userRegisterDTO = createTestUserRegisterDTO();

        assertThrows(NicknameExistsException.class, () -> userValidationService.validateUserRegisterDTO(userRegisterDTO));

        verifyUserRepositoryExistsByNicknameCalledOnce(NICKNAME);
    }

    private void verifyUserRepositoryExistsByNicknameCalledOnce(String calledNickname) {
        verify(userRepository, times(1)).existsByNickname(calledNickname);
    }

    @Test
    public void whenRegisterNewUserWithExistingEmailShouldThrowIncorrectEmailException() {
        mockExistsByEmailToReturn(EMAIL, true);
        UserRegisterDTO userRegisterDTO = createTestUserRegisterDTO();

        assertThrows(EmailExistsException.class, () -> userValidationService.validateUserRegisterDTO(userRegisterDTO));

        verifyUserRepositoryExistsByEmailCalledOnce(EMAIL);
    }

    private void verifyUserRepositoryExistsByEmailCalledOnce(String calledEmail) {
        verify(userRepository, times(1)).existsByEmail(calledEmail);
    }

    private UserRegisterDTO createTestUserRegisterDTO() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setEmail(EMAIL);
        userRegisterDTO.setFirstName(FIRSTNAME);
        userRegisterDTO.setLastName(LASTNAME);
        userRegisterDTO.setNickname(NICKNAME);
        userRegisterDTO.setPassword(PASSWORD);

        return userRegisterDTO;
    }
}