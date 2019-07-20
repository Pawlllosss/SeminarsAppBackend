package pl.oczadly.spring.topics.course.control;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.oczadly.spring.topics.course.entity.Course;
import pl.oczadly.spring.topics.course.repository.CourseNotFoundException;
import pl.oczadly.spring.topics.course.repository.CourseRepository;

import java.util.List;

@Service
@Transactional
public class CourseServiceImplementation implements CourseService {

    private CourseRepository courseRepository;

    public CourseServiceImplementation(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Course course, Long id) {
        Course courseToUpdate = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
        courseToUpdate.setName(course.getName());
        Course updatedCourse = courseRepository.save(courseToUpdate);

        return updatedCourse;
    }

    @Override
    public void deleteCourse(Long id) {
        if(!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }

        courseRepository.deleteById(id);
    }
}
