package pl.oczadly.spring.topics.domain.role.entity.exception;

import pl.oczadly.spring.topics.application.exception.NotFoundException;

public class RoleNotFoundException extends NotFoundException {

    public RoleNotFoundException(Long id) {
        super("Role not found: " + id);
    }
}
