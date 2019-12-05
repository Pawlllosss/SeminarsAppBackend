package pl.oczadly.spring.topics.domain.user.management.entity.exception;

public class NicknameExistsException extends RuntimeException {

    public NicknameExistsException(String nickname) {
        super("User with nickname " + nickname + " already exists.");
    }
}
