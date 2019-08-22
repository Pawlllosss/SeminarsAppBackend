package pl.oczadly.spring.topics.course.boundary;

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
import pl.oczadly.spring.topics.course.control.CourseService;
import pl.oczadly.spring.topics.course.entity.Course;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/course")
public class CourseRestController {

    private CourseService courseService;

    private CourseResourceAssembler courseResourceAssembler;

    @GetMapping(produces = { "application/hal+json" })
    public Resources<Resource<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        List<Resource<Course>> coursesResponse = courses.stream()
                .map(courseResourceAssembler::toResource)
                .collect(Collectors.toList());

        Link selfLink = linkTo(CourseRestController.class).withSelfRel();
        return new Resources<>(coursesResponse, selfLink);
    }

    @GetMapping(value = "/{id}", produces = { "application/hal+json"})
    public Resource<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return courseResourceAssembler.toResource(course);
    }

    @PostMapping(produces = { "application/hal+json"})
    @PreAuthorize("hasAuthority('CRUD_ALL_COURSES')")
    public ResponseEntity<Resource<Course>> createCourse(@RequestBody Course course) {
        Course persistedCourse = courseService.createCourse(course);

        return ResponseEntity.created(linkTo(methodOn(CourseRestController.class)
                .getCourseById(persistedCourse.getId()))
                .toUri())
                .body(courseResourceAssembler.toResource(persistedCourse));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CRUD_ALL_COURSES')")
    public ResponseEntity<Resource<Course>> updateCourse(@RequestBody Course course, @PathVariable Long id) {
        Course updatedCourse = courseService.updateCourse(course, id);

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
