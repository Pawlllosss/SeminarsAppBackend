package pl.oczadly.spring.topics.user.management.control;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.oczadly.spring.topics.user.management.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOptionalByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByNickname(String nickname);
}
