package pl.oczadly.spring.topics.course.entity;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long id) {
        super("Course not found: " + id);
    }
}
