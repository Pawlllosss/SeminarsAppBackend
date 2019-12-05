package pl.oczadly.spring.topics.domain.course.control;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.oczadly.spring.topics.domain.course.entity.Course;
import pl.oczadly.spring.topics.domain.course.entity.dto.CourseDTO;
import pl.oczadly.spring.topics.domain.course.entity.exception.CourseNotFoundException;
import pl.oczadly.spring.topics.domain.role.control.RoleService;
import pl.oczadly.spring.topics.domain.role.entity.CourseVoterRole;
import pl.oczadly.spring.topics.domain.role.entity.Role;
import pl.oczadly.spring.topics.domain.user.management.control.UserService;
import pl.oczadly.spring.topics.domain.user.management.entity.User;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImplementation implements CourseService {

    private CourseRepository courseRepository;

    private UserService userService;
    private RoleService roleService;

    private ModelMapper mapper;

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
    public List<Course> getAvailableCoursesForUserId(Long userId) {
        User user = userService.getUserById(userId);
        Set<Long> availableCourseIds = getCoursesIdAvailableForUser(user);

        return courseRepository.findAllById(availableCourseIds);
    }

    private Set<Long> getCoursesIdAvailableForUser(User user) {
        Set<Role> roles = user.getRoles();

        return roles.stream()
                .map(Role::getCourseVoterRole)
                .filter(Objects::nonNull)
                .map(CourseVoterRole::getCourse)
                .map(Course::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isCourseAvailableForUserId(Long userId, Long courseId) {
        if(!isCourseExist(courseId)) {
            throw new CourseNotFoundException(courseId);
        }

        User user = userService.getUserById(userId);
        Set<Long> availableCourseIds = getCoursesIdAvailableForUser(user);

        return availableCourseIds.contains(courseId);
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
    }

    @Override
    public Course createCourse(CourseDTO courseDTO) {
        Course course = mapper.map(courseDTO, Course.class);
        course.setActive(true);
        String courseName = course.getName();
        CourseVoterRole courseVoterRole = roleService.createCourseVoterRole(courseName);
        course.setCourseVoterRole(courseVoterRole);
        Course persistedCourse = courseRepository.save(course);

        return persistedCourse;
    }

    @Override
    public Course updateCourse(CourseDTO courseDTO, Long id) {
        Course courseToUpdate = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
        courseToUpdate.setName(courseDTO.getName());
        courseToUpdate.setVoteEndDate(courseDTO.getVoteEndDate());
        Course updatedCourse = courseRepository.save(courseToUpdate);

        return updatedCourse;
    }

    @Override
    public void deleteCourse(Long id) {
        if(!isCourseExist(id)) {
            throw new CourseNotFoundException(id);
        }

        courseRepository.deleteById(id);
    }

    private boolean isCourseExist(Long id) {
        return courseRepository.existsById(id);
    }

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }
}
