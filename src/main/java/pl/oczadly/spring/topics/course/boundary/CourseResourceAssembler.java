package pl.oczadly.spring.topics.course.boundary;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.oczadly.spring.topics.course.entity.Course;
import pl.oczadly.spring.topics.topic.boundary.TopicRestController;
import pl.oczadly.spring.topics.topic.entity.TopicDTO;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CourseResourceAssembler implements ResourceAssembler<Course, Resource<Course>> {

    @Override
    public Resource<Course> toResource(Course course) {
        Long courseId = course.getId();

        Link selfLink = linkTo(methodOn(CourseRestController.class).getCourseById(courseId))
                .withSelfRel();
        Link updateLink = linkTo(methodOn(CourseRestController.class).updateCourse(course, courseId))
                .withRel("update");
        Link deleteLink = linkTo(methodOn(CourseRestController.class).deleteCourse(courseId))
                .withRel("delete");
        Link topicsLink = linkTo(methodOn(TopicRestController.class).getTopicByCourseId(courseId))
                .withRel("topics");
        Link createTopicLink = linkTo(methodOn(TopicRestController.class).createTopic(new TopicDTO(), courseId))
                .withRel("createTopic");

        return new Resource<>(course, selfLink, updateLink, deleteLink, topicsLink, createTopicLink);
    }
}
