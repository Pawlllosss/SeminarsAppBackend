package pl.oczadly.spring.topics.user.management.entity.exception;

public class NicknameExistsException extends RuntimeException {

    public NicknameExistsException(String nickname) {
        super("User with nickname " + nickname + " already exists.");
    }
}
