package pl.oczadly.spring.topics.course.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.oczadly.spring.topics.course.entity.Course;
import pl.oczadly.spring.topics.course.entity.CourseNotFoundException;
import pl.oczadly.spring.topics.role.control.RoleService;
import pl.oczadly.spring.topics.role.entity.CourseVoterRole;

import java.util.List;

@Service
@Transactional
public class CourseServiceImplementation implements CourseService {

    private CourseRepository courseRepository;

    private RoleService roleService;

    public CourseServiceImplementation() {
    }

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
        String courseName = course.getName();
        CourseVoterRole courseVoterRole = roleService.createCourseVoterRole(courseName);
        course.setCourseVoterRole(courseVoterRole);
        Course persistedCourse = courseRepository.save(course);

        return persistedCourse;
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

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
}
