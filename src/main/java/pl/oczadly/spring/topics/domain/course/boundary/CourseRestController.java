package pl.oczadly.spring.topics.domain.course.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.oczadly.spring.topics.domain.course.control.CourseService;
import pl.oczadly.spring.topics.domain.course.entity.Course;
import pl.oczadly.spring.topics.domain.course.entity.dto.CourseDTO;
import pl.oczadly.spring.topics.domain.user.authentication.boundary.annotation.CurrentUser;
import pl.oczadly.spring.topics.domain.user.authentication.entity.UserAuthenticationDetails;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/course", produces = { "application/hal+json" })
public class CourseRestController {

    private CourseService courseService;

    private CourseResourceAssembler courseResourceAssembler;

    @GetMapping
    public Resources<Resource<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        Resources<Resource<Course>> courseResources = mapCoursesToResources(courses);

        return courseResources;
    }

    private Resources<Resource<Course>> mapCoursesToResources(List<Course> courses) {
        List<Resource<Course>> coursesResponse = courses.stream()
                .map(courseResourceAssembler::toResource)
                .collect(Collectors.toList());
        Link selfLink = linkTo(CourseRestController.class).withSelfRel();

        return new Resources<>(coursesResponse, selfLink);
    }

    @GetMapping("/currentUser")
    public Resources<Resource<Course>> getAvailableCoursesForCurrentUser(@CurrentUser UserAuthenticationDetails currentUserAuthenticationDetails) {
        Long userId = currentUserAuthenticationDetails.getId();
        List<Course> courses = courseService.getAvailableCoursesForUserId(userId);
        Resources<Resource<Course>> courseResources = mapCoursesToResources(courses);

        return courseResources;
    }
    @GetMapping("/{id}")
    public Resource<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return courseResourceAssembler.toResource(course);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CRUD_ALL_COURSES')")
    public ResponseEntity<Resource<Course>> createCourse(@RequestBody CourseDTO courseDTO) {
        Course persistedCourse = courseService.createCourse(courseDTO);

        return ResponseEntity.created(linkTo(methodOn(CourseRestController.class)
                .getCourseById(persistedCourse.getId()))
                .toUri())
                .body(courseResourceAssembler.toResource(persistedCourse));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CRUD_ALL_COURSES')")
    public ResponseEntity<Resource<Course>> updateCourse(@RequestBody CourseDTO courseDTO, @PathVariable Long id) {
        Course updatedCourse = courseService.updateCourse(courseDTO, id);

        return ResponseEntity.created(linkTo(methodOn(CourseRestController.class)
                .getCourseById(id))
                .toUri())
                .body(courseResourceAssembler.toResource(updatedCourse));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CRUD_ALL_COURSES')")
    public ResponseEntity<Course> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent()
                .build();
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setCourseResourceAssembler(CourseResourceAssembler courseResourceAssembler) {
        this.courseResourceAssembler = courseResourceAssembler;
    }
}
