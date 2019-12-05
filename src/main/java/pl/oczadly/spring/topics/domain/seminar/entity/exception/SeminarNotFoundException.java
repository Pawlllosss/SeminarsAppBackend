package pl.oczadly.spring.topics.domain.seminar.entity.exception;

import pl.oczadly.spring.topics.application.exception.NotFoundException;

public class SeminarNotFoundException extends NotFoundException {

    public SeminarNotFoundException(Long id) {
        super("Seminar not found: " + id);
    }
}
