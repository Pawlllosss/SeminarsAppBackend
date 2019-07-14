package pl.oczadly.spring.topics.course.controller;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.oczadly.spring.topics.course.entity.Course;
import pl.oczadly.spring.topics.course.entity.CourseNotFoundException;
import pl.oczadly.spring.topics.course.entity.CourseResourceAssembler;
import pl.oczadly.spring.topics.course.repository.CourseRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/course")
public class CourseController {

    private CourseRepository courseRepository;

    private CourseResourceAssembler courseResourceAssembler;

    public CourseController(CourseRepository courseRepository, CourseResourceAssembler courseResourceAssembler) {
        this.courseRepository = courseRepository;
        this.courseResourceAssembler = courseResourceAssembler;
    }

    @GetMapping(produces = { "application/hal+json" })
    public Resources<Resource<Course>> getAll() {
        List<Resource<Course>> courses = courseRepository.findAll().stream()
                .map(courseResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(courses);
    }

    @GetMapping(value = "/{id}", produces = { "application/hal+json"})
    public Resource<Course> getById(@PathVariable Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));

        return courseResourceAssembler.toResource(course);
    }

    @PostMapping(produces = { "application/hal+json"})
    public ResponseEntity<Resource<Course>> createCourse(@RequestBody Course course) {
        Course persistedCourse = courseRepository.save(course);

        return ResponseEntity.created(linkTo(methodOn(CourseController.class)
                .getById(persistedCourse.getId()))
                .toUri())
                .body(courseResourceAssembler.toResource(persistedCourse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resource<Course>> updateCourse(@RequestBody Course course, @PathVariable Long id) {
        Course courseToUpdate = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));

        courseToUpdate.setName(course.getName());
        Course updatedCourse = courseRepository.save(courseToUpdate);

        return ResponseEntity.created(linkTo(methodOn(CourseController.class)
                .getById(id))
                .toUri())
                .body(courseResourceAssembler.toResource(updatedCourse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable Long id) {
        if(!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }

        courseRepository.deleteById(id);
        return ResponseEntity.noContent()
                .build();
    }

}
