package pl.oczadly.spring.topics.user.management.entity.exception;

public class EmailExistsException extends RuntimeException {

    public EmailExistsException(String email) {
        super("User with email " + email + " already exists.");
    }
}
