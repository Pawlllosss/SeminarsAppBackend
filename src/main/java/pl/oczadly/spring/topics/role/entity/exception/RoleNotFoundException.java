package pl.oczadly.spring.topics.role.entity.exception;

import pl.oczadly.spring.topics.exception.NotFoundException;

public class RoleNotFoundException extends NotFoundException {

    public RoleNotFoundException(Long id) {
        super("Role not found: " + id);
    }
}
