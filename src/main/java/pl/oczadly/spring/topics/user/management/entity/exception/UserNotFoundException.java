package pl.oczadly.spring.topics.user.management.entity.exception;

import pl.oczadly.spring.topics.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(Long id) {
        super("User not found: " + id);
    }

    public UserNotFoundException(String email) {
        super("User not found: " + email);
    }
}
