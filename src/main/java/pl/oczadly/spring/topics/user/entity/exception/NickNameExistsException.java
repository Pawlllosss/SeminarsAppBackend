package pl.oczadly.spring.topics.user.entity.exception;

public class NickNameExistsException extends RuntimeException {

    public NickNameExistsException(String nickName) {
        super("User with nick name " + nickName + " already exists.");
    }
}
