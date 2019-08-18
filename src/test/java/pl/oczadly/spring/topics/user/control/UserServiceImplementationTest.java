package pl.oczadly.spring.topics.user.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.oczadly.spring.topics.role.Role;
import pl.oczadly.spring.topics.role.RoleRepository;
import pl.oczadly.spring.topics.user.entity.User;
import pl.oczadly.spring.topics.user.entity.dto.UserRegisterDTO;
import pl.oczadly.spring.topics.user.entity.exception.UserNotFoundException;
import pl.oczadly.spring.topics.user.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@ExtendWith(SpringExtension.class)
public class UserServiceImplementationTest {

    private static final String USER1_NICKNAME = "Sztosław";
    private static final String USER2_NICKNAME = "Faplo";
    private static final String USER1_EMAIL = "sztos@sztoslaw.com";
    private static final String USER2_EMAIL = "faplo@faplo.com";
    private static final String NOT_EXISTING_EMAIL = "not@existing.com";
    private static final String USER1_FIRSTNAME = "Stefan";
    private static final String USER2_FIRSTNAME = "Paweł";
    private static final String USER1_LASTNAME = "Sztosłowicz";
    private static final String USER2_LASTNAME = "Anon";
    private static final String USER1_PASSWORD = "bla4Bla!";
    private static final String ROLE_USER = "USER";
    private static final Long USER1_ID = 1L;
    private static final Long NOT_EXISTING_ID = 111L;
    private static final User USER1 = new User();
    private static final User USER2 = new User();

    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
    private PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

    private UserService userService = createUserServiceImplementation(userRepository, passwordEncoder);

    @BeforeEach
    private void prepareMocks() {
        USER1.setId(USER1_ID);
        USER1.setEmail(USER1_EMAIL);
        USER1.setNickname(USER1_NICKNAME);
        USER1.setFirstName(USER1_FIRSTNAME);
        USER1.setLastName(USER1_LASTNAME);

        USER2.setEmail(USER2_EMAIL);
        USER2.setNickname(USER2_NICKNAME);
        USER2.setFirstName(USER2_FIRSTNAME);
        USER2.setLastName(USER2_LASTNAME);

        final List<User> allUsers = List.of(USER1, USER2);

        given(userRepository.findAll()).willReturn(allUsers);
        given(userRepository.findById(USER1_ID)).willReturn(Optional.of(USER1));
        given(userRepository.findOptionalByEmail(USER1_EMAIL)).willReturn(Optional.of(USER1));
        given(userRepository.save(any())).willAnswer(invocation -> invocation.getArgument(0));

        Role userRole = new Role(ROLE_USER, Collections.emptySet());
        given(roleRepository.findOptionalByName(ROLE_USER)).willReturn(Optional.of(userRole));
    }


    //TODO: role repository as argument
    private UserService  createUserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        ModelMapper mapper = new ModelMapper();

        UserServiceImplementation userServiceImplementation = new UserServiceImplementation();
        userServiceImplementation.setUserRepository(userRepository);
        userServiceImplementation.setRoleRepository(roleRepository);
        userServiceImplementation.setPasswordEncoder(passwordEncoder);
        userServiceImplementation.setMapper(mapper);

        return userServiceImplementation;
    }


    @Test
    public void whenGetAllUsersThenShouldReturnAllUsers() {
        List<User> usersFromService = userService.getAllUsers();
        final int expectedUsersSize = 2;
        verifyUserRepositoryFindAllCalledOnce();

        assertThat(usersFromService).hasSize(expectedUsersSize);

        assertThat(usersFromService).extracting(User::getEmail)
                .containsOnly(USER1_EMAIL, USER2_EMAIL);

        assertThat(usersFromService).extracting(User::getNickname)
                .containsOnly(USER1_NICKNAME, USER2_NICKNAME);

        assertThat(usersFromService).extracting(User::getFirstName)
                .containsOnly(USER1_FIRSTNAME, USER2_FIRSTNAME);

        assertThat(usersFromService).extracting(User::getLastName)
                .containsOnly(USER1_LASTNAME, USER2_LASTNAME);
    }

    @Test
    public void whenGetUserByExistingIdThenReturnUser() {
        User userFromService = userService.getUserById(USER1_ID);
        verifyUserRepositoryFindByIdCalledOnce(USER1_ID);
        assertThat(userFromService.getId()).isEqualTo(USER1_ID);
        assertThat(userFromService.getEmail()).isEqualTo(USER1_EMAIL);
        assertThat(userFromService.getFirstName()).isEqualTo(USER1_FIRSTNAME);
        assertThat(userFromService.getLastName()).isEqualTo(USER1_LASTNAME);
        assertThat(userFromService.getEmail()).isEqualTo(USER1_EMAIL);
    }

    @Test
    public void whenGetUserByNotExistingIdThenTrowUserNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(NOT_EXISTING_ID));
    }

    @Test
    void whenGetUserByExistingEmailThenReturnUser() {
        User userFromService = userService.getUserByEmail(USER1_EMAIL);
        verifyUserRepositoryFindOptionalByEmailCalledOnce(USER1_EMAIL);
        assertThat(userFromService.getId()).isEqualTo(USER1_ID);
        assertThat(userFromService.getEmail()).isEqualTo(USER1_EMAIL);
        assertThat(userFromService.getFirstName()).isEqualTo(USER1_FIRSTNAME);
        assertThat(userFromService.getLastName()).isEqualTo(USER1_LASTNAME);
        assertThat(userFromService.getEmail()).isEqualTo(USER1_EMAIL);
    }

    @Test
    public void whenGetUserByNotExistingEmailThenThrowUserNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail(NOT_EXISTING_EMAIL));
        verifyUserRepositoryFindOptionalByEmailCalledOnce(NOT_EXISTING_EMAIL);
    }


    @Test
    public void whenRegisterNewUserWithNonExistingCorrectEmailShouldReturnNewUser() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setEmail(USER1_EMAIL);
        userRegisterDTO.setFirstName(USER1_FIRSTNAME);
        userRegisterDTO.setLastName(USER1_LASTNAME);
        userRegisterDTO.setNickname(USER1_NICKNAME);
        userRegisterDTO.setPassword(USER1_PASSWORD);

        User registredUser = userService.registerNewUser(userRegisterDTO);
        assertThat(registredUser.getEmail()).isEqualTo(USER1_EMAIL);
        assertThat(registredUser.getFirstName()).isEqualTo(USER1_FIRSTNAME);
        assertThat(registredUser.getLastName()).isEqualTo(USER1_LASTNAME);
        assertThat(registredUser.getEmail()).isEqualTo(USER1_EMAIL);
        assertThat(registredUser.getRoles()).extracting(Role::getName).containsOnly("USER");
    }

    @Test
    public void whenRegisterNewUserWithExistingEmailShouldThrowEmailExistsException() {

    }

    @Test
    public void whenRegisterNewUserWithExistingNicknameShouldThrowNicknameExistsException() {

    }

    @Test
    public void whenRegisterNewUserWithIncorrectEmailShouldThrowIncorrectEmailException() {

    }

    private void verifyUserRepositoryFindAllCalledOnce() {
        verify(userRepository, times(1)).findAll();
    }

    private void verifyUserRepositoryFindByIdCalledOnce(Long calledId) {
        verify(userRepository, times(1)).findById(calledId);
    }

    private void verifyUserRepositoryFindOptionalByEmailCalledOnce(String calledEmail) {
        verify(userRepository, times(1)).findOptionalByEmail(calledEmail);
    }
}
