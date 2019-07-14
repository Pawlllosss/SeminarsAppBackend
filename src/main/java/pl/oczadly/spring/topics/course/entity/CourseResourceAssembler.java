package pl.oczadly.spring.topics.course.entity;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.oczadly.spring.topics.course.controller.CourseController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CourseResourceAssembler implements ResourceAssembler<Course, Resource<Course>> {

    @Override
    public Resource<Course> toResource(Course course) {
        Long courseId = course.getId();
        Link getAllLink = linkTo(methodOn(CourseController.class).getAll())
                .withRel("courses");
        Link updateLink = linkTo(methodOn(CourseController.class).updateCourse(course, courseId))
                .withRel("update");
        Link deleteLink = linkTo(methodOn(CourseController.class).deleteCourse(courseId))
                .withRel("delete");

        return new Resource<>(course, getAllLink, updateLink, deleteLink);
    }
}
