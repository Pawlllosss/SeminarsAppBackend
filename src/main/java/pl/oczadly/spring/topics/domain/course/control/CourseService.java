package pl.oczadly.spring.topics.domain.course.control;

import pl.oczadly.spring.topics.domain.course.entity.Course;
import pl.oczadly.spring.topics.domain.course.entity.dto.CourseDTO;

import java.util.List;

public interface CourseService {

    List<Course> getAllCourses();

    List<Course> getAvailableCoursesForUserId(Long userId);

    boolean isCourseAvailableForUserId(Long userId, Long courseId);

    Course getCourseById(Long id);

    Course createCourse(CourseDTO courseDTO);

    Course updateCourse(CourseDTO courseDTO, Long id);

    void deleteCourse(Long id);
}
