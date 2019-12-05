package pl.oczadly.spring.topics.domain.user.management.entity.exception;

import pl.oczadly.spring.topics.application.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(Long id) {
        super("User not found: " + id);
    }

    public UserNotFoundException(String email) {
        super("User not found: " + email);
    }
}
