package pl.oczadly.spring.topics.seminar.entity;

import pl.oczadly.spring.topics.exception.NotFoundException;

public class SeminarNotFoundException extends NotFoundException {

    public SeminarNotFoundException(Long id) {
        super("Seminar not found: " + id);
    }
}
