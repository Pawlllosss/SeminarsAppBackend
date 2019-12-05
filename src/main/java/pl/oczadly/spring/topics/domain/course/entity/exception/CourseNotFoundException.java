package pl.oczadly.spring.topics.domain.course.entity.exception;

import pl.oczadly.spring.topics.application.exception.NotFoundException;

public class CourseNotFoundException extends NotFoundException {
    public CourseNotFoundException(Long id) {
        super("Course not found: " + id);
    }
}
