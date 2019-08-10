package pl.oczadly.spring.topics.course.entity;

import pl.oczadly.spring.topics.exception.NotFoundException;

public class CourseNotFoundException extends NotFoundException {
    public CourseNotFoundException(Long id) {
        super("Course not found: " + id);
    }
}
