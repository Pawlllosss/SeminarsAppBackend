package pl.oczadly.spring.topics.domain.user.management.entity.exception;

public class EmailExistsException extends RuntimeException {

    public EmailExistsException(String email) {
        super("User with email " + email + " already exists.");
    }
}
