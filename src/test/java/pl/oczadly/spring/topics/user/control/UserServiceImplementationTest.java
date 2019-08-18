package pl.oczadly.spring.topics.user.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.oczadly.spring.topics.user.entity.User;
import pl.oczadly.spring.topics.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@ExtendWith(SpringExtension.class)
public class UserServiceImplementationTest {

    private static final String USER1_NICKNAME = "Sztosław";
    private static final String USER2_NICKNAME = "Faplo";
    private static final String USER1_EMAIL = "sztos@sztoslaw.com";
    private static final String USER2_EMAIL = "faplo@faplo.com";
    private static final String USER1_FIRSTNAME = "Stefan";
    private static final String USER2_FIRSTNAME = "Paweł";
    private static final String USER1_LASTNAME = "Sztosłowicz";
    private static final String USER2_LASTNAME = "Anon";
    private static final Long USER1_ID = 1L;
    private static final Long NOT_EXISTING_ID = 111L;
    private static final User USER1 = new User();
    private static final User USER2 = new User();

    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private UserService userService = createUserServiceImplementation(userRepository);

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

    }

    private UserService  createUserServiceImplementation(UserRepository userRepository) {
        UserServiceImplementation userServiceImplementation = new UserServiceImplementation();
        userServiceImplementation.setUserRepository(userRepository);
        return userServiceImplementation;
    }


    @Test
    public void getAllUsers() {
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

    private void verifyUserRepositoryFindAllCalledOnce() {
        verify(userRepository, times(1)).findAll();
    }
}

//
//package pl.oczadly.spring.topics.user.control;
//
//        import org.junit.jupiter.api.Test;
//        import org.junit.runner.RunWith;
//        import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.boot.test.context.TestConfiguration;
//        import org.springframework.boot.test.mock.mockito.MockBean;
//        import org.springframework.context.annotation.Bean;
//        import org.springframework.test.context.junit4.SpringRunner;
//        import pl.oczadly.spring.topics.user.repository.UserRepository;
//
//@RunWith(SpringRunner.class)
//public class UserServiceImplementationTest {
////
////    private static final String USER1_NICKNAME = "Sztosław";
////    private static final String USER2_NICKNAME = "Faplo";
////    private static final String USER1_EMAIL = "sztos@sztoslaw.com";
////    private static final String USER2_EMAIL = "faplo@faplo.com";
////    private static final String USER1_FIRSTNAME = "Stefan";
////    private static final String USER2_FIRSTNAME = "Paweł";
////    private static final String USER1_LASTNAME = "Sztosłowicz";
////    private static final String USER2_LASTNAME = "Anon";
////    private static final Long USER1_ID = 1L;
////    private static final Long NOT_EXISTING_ID = 111L;
////    private static final User USER1 = new User();
////    private static final User USER2 = new User();
//
//
//    @TestConfiguration
//    static class UserServiceImplementationTestConfiguration {
//        @Bean
//        public UserService userService() {
//            return new UserServiceImplementation();
//        }
//    }
//
//    private UserService userService;
//
//    @MockBean
//    private UserRepository userRepository;
//
////    @Before
////    public void setUpMocks() {
////        USER1.setId(USER1_ID);
////        USER1.setEmail(USER1_EMAIL);
////        USER1.setNickname(USER1_NICKNAME);
////        USER1.setFirstName(USER1_FIRSTNAME);
////        USER1.setLastName(USER1_LASTNAME);
////
////        USER2.setId(USER1_ID);
////        USER2.setEmail(USER1_EMAIL);
////        USER2.setNickname(USER1_NICKNAME);
////        USER2.setFirstName(USER1_FIRSTNAME);
////        USER2.setLastName(USER1_LASTNAME);
////
////        final List<User> allUsers = List.of(USER1, USER2);
////
////        given(userRepository.findAll()).willReturn(allUsers);
////        given(userRepository.findById(USER1_ID)).willReturn(Optional.of(USER1));
////    }
////
////
////    @Test
////    public void getAllUsers() {
////        List<User> usersFromService = userService.getAllUsers();
////        final int expectedUsersSize = 2;
////        verifyUserRepositoryFindAllCalledOnce();
////
////        assertThat(usersFromService).hasSize(expectedUsersSize);
////
////        assertThat(usersFromService).extracting(User::getEmail)
////                .containsOnly(USER1_EMAIL, USER2_EMAIL);
////
////        assertThat(usersFromService).extracting(User::getNickname)
////                .containsOnly(USER1_NICKNAME, USER2_NICKNAME);
////
////        assertThat(usersFromService).extracting(User::getFirstName)
////                .containsOnly(USER1_FIRSTNAME, USER2_FIRSTNAME);
////
////        assertThat(usersFromService).extracting(User::getLastName)
////                .containsOnly(USER1_LASTNAME, USER2_LASTNAME);
////    }
////
////    @Test
////    public void getUserById() {
////    }
////
////    @Test
////    void getUserByEmail() {
////    }
//
//    @Test
//    public void registerNewUser() {
//
//        //TODO: check if has user privileges
//    }
//

//
//    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }
//}