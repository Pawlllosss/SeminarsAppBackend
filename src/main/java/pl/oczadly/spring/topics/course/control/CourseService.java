package pl.oczadly.spring.topics.course.control;

import pl.oczadly.spring.topics.course.entity.Course;

import java.util.List;

public interface CourseService {

    List<Course> getAllCourses();

    List<Course> getAvailableCoursesForUserId(Long userId);

    Course getCourseById(Long id);

    Course createCourse(Course course);

    Course updateCourse(Course course, Long id);

    void deleteCourse(Long id);
}
