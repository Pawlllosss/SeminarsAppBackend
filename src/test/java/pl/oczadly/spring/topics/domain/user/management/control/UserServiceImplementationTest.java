package pl.oczadly.spring.topics.domain.user.management.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.oczadly.spring.topics.domain.role.entity.Role;
import pl.oczadly.spring.topics.domain.role.control.RoleRepository;
import pl.oczadly.spring.topics.domain.user.management.entity.User;
import pl.oczadly.spring.topics.domain.user.management.entity.dto.UserRegisterDTO;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplementationTest {

    private static final String USER1_NICKNAME = "Sztosław";
    private static final String USER2_NICKNAME = "Faplo";
    private static final String USER1_EMAIL = "sztos@sztoslaw.com";
    private static final String USER2_EMAIL = "faplo@faplo.com";
    private static final String USER1_FIRSTNAME = "Stefan";
    private static final String USER2_FIRSTNAME = "Paweł";
    private static final String USER1_LASTNAME = "Sztosłowicz";
    private static final String USER2_LASTNAME = "Anon";
    private static final String USER1_PASSWORD = "bla4Bla!";
    private static final String ROLE_USER = "USER";
    private static final Long USER1_ID = 1L;
    private static final User USER1 = new User();
    private static final User USER2 = new User();

    @Mock
    private UserValidationService userValidationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImplementation userService;

    @BeforeEach
    private void prepareUsers() {
        USER1.setId(USER1_ID);
        USER1.setEmail(USER1_EMAIL);
        USER1.setNickname(USER1_NICKNAME);
        USER1.setFirstName(USER1_FIRSTNAME);
        USER1.setLastName(USER1_LASTNAME);

        USER2.setEmail(USER2_EMAIL);
        USER2.setNickname(USER2_NICKNAME);
        USER2.setFirstName(USER2_FIRSTNAME);
        USER2.setLastName(USER2_LASTNAME);
    }

    @BeforeEach
    private void setMapper() {
        ModelMapper modelMapper = new ModelMapper();
        userService.setMapper(modelMapper);
    }

    @Test
    public void whenGetAllUsersThenShouldReturnAllUsers() {
        mockFindAllUsers();

        final int expectedUsersSize = 2;
        List<User> usersFromService = userService.getAllUsers();
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

    private void mockFindAllUsers() {
        final List<User> allUsers = List.of(USER1, USER2);
        given(userRepository.findAll()).willReturn(allUsers);
    }

    private void verifyUserRepositoryFindAllCalledOnce() {
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void whenGetUserByExistingIdThenReturnUser() {
        mockFindById(USER1_ID, USER1);

        User userFromService = userService.getUserById(USER1_ID);
        verifyUserRepositoryFindByIdCalledOnce(USER1_ID);

        assertThat(userFromService.getId()).isEqualTo(USER1_ID);
        assertThat(userFromService.getEmail()).isEqualTo(USER1_EMAIL);
        assertThat(userFromService.getFirstName()).isEqualTo(USER1_FIRSTNAME);
        assertThat(userFromService.getLastName()).isEqualTo(USER1_LASTNAME);
        assertThat(userFromService.getEmail()).isEqualTo(USER1_EMAIL);
    }

    private void mockFindById(Long userId, User user) {
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
    }

    private void verifyUserRepositoryFindByIdCalledOnce(Long calledId) {
        verify(userRepository, times(1)).findById(calledId);
    }

    @Test
    void whenGetUserByExistingEmailThenReturnUser() {
        mockFindOptionalByEmail(USER1_EMAIL, USER1);

        User userFromService = userService.getUserByEmail(USER1_EMAIL);
        verifyUserRepositoryFindOptionalByEmailCalledOnce(USER1_EMAIL);

        assertThat(userFromService.getId()).isEqualTo(USER1_ID);
        assertThat(userFromService.getEmail()).isEqualTo(USER1_EMAIL);
        assertThat(userFromService.getFirstName()).isEqualTo(USER1_FIRSTNAME);
        assertThat(userFromService.getLastName()).isEqualTo(USER1_LASTNAME);
        assertThat(userFromService.getEmail()).isEqualTo(USER1_EMAIL);
    }

    private void mockFindOptionalByEmail(String email, User user) {
        given(userRepository.findOptionalByEmail(email)).willReturn(Optional.of(user));
    }

    private void verifyUserRepositoryFindOptionalByEmailCalledOnce(String calledEmail) {
        verify(userRepository, times(1)).findOptionalByEmail(calledEmail);
    }

    @Test
    public void whenRegisterNewUserHappyPathScenarioShouldReturnNewUser() {
        mockSaveUser();
        mockUserRole();

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setEmail(USER1_EMAIL);
        userRegisterDTO.setFirstName(USER1_FIRSTNAME);
        userRegisterDTO.setLastName(USER1_LASTNAME);
        userRegisterDTO.setNickname(USER1_NICKNAME);
        userRegisterDTO.setPassword(USER1_PASSWORD);

        User registeredUser = userService.registerNewUser(userRegisterDTO);
        assertThat(registeredUser.getEmail()).isEqualTo(USER1_EMAIL);
        assertThat(registeredUser.getFirstName()).isEqualTo(USER1_FIRSTNAME);
        assertThat(registeredUser.getLastName()).isEqualTo(USER1_LASTNAME);
        assertThat(registeredUser.getEmail()).isEqualTo(USER1_EMAIL);
        assertThat(registeredUser.getRoles()).extracting(Role::getName).containsOnly("USER");
    }

    private void mockSaveUser() {
        given(userRepository.save(any())).willAnswer(invocation -> invocation.getArgument(0));
    }

    private void mockUserRole() {
        Role userRole = new Role(ROLE_USER, Collections.emptySet());
        given(roleRepository.findOptionalByName(ROLE_USER)).willReturn(Optional.of(userRole));
    }
}
