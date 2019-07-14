package pl.oczadly.spring.topics.course.repository;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long id) {
        super("Course not found: " + id);
    }
}
