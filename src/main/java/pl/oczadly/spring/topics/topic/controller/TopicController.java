package pl.oczadly.spring.topics.topic.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
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
import org.springframework.web.client.ResourceAccessException;
import pl.oczadly.spring.topics.course.entity.Course;
import pl.oczadly.spring.topics.course.repository.CourseRepository;
import pl.oczadly.spring.topics.topic.entity.Topic;
import pl.oczadly.spring.topics.topic.entity.TopicNotFoundException;
import pl.oczadly.spring.topics.topic.entity.TopicResourceAssembler;
import pl.oczadly.spring.topics.topic.repository.TopicRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/topic")
public class TopicController {

    private TopicRepository topicRepository;
    private CourseRepository courseRepository;

    private TopicResourceAssembler topicResourceAssembler;

    public TopicController(TopicRepository topicRepository, CourseRepository courseRepository, TopicResourceAssembler topicResourceAssembler) {
        this.topicRepository = topicRepository;
        this.courseRepository = courseRepository;
        this.topicResourceAssembler = topicResourceAssembler;
    }

    @GetMapping(produces = { "application/hal+json" })
    public Resources<Resource<Topic>> getAll() {
        List<Resource<Topic>> topics = topicRepository.findAll().stream()
                .map(topicResourceAssembler::toResource)
                .collect(Collectors.toList());

        Link link = linkTo(TopicController.class).withSelfRel();
        return new Resources<>(topics, link);
    }

    @GetMapping(value = "/{id}", produces = { "application/hal+json" })
    public Resource<Topic> getById(@PathVariable Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException(id));

        return topicResourceAssembler.toResource(topic);
    }

    @GetMapping(value = "/course/{courseId}", produces = { "application/hal+json" })
    public Resources<Resource<Topic>> getByCourseId(@PathVariable Long courseId) {
        List<Resource<Topic>> topics = topicRepository.findByCourseId(courseId).stream()
                .map(topicResourceAssembler::toResource)
                .collect(Collectors.toList());

        Link link = linkTo(TopicController.class).withSelfRel();
        return new Resources<>(topics, link);
    }

    @PostMapping(value = "/course/{courseId}", produces = { "application/hal+json" })
    public ResponseEntity<Resource<Topic>> addTopic(@RequestBody Topic topic,
                                                    @PathVariable Long courseId) {
        Course relatedCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceAccessException("Course with give id not found!"));

        topic.setCourse(relatedCourse);
        Topic persistedTopic = topicRepository.save(topic);

        return ResponseEntity
                .created(linkTo(methodOn(TopicController.class)
                        .getById(persistedTopic.getId()))
                        .toUri())
                .body(topicResourceAssembler.toResource(persistedTopic));
    }

    @PutMapping(value = "/{id}", produces = { "application/hal+json" })
    public ResponseEntity<Resource<Topic>> updateTopic(@RequestBody Topic topic,
                                                       @PathVariable Long id) {
        Topic topicToUpdate = topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException(id));

        topicToUpdate.setName(topic.getName());
        topicToUpdate.setDescription(topic.getDescription());

        Topic updatedTopic = topicRepository.save(topicToUpdate);
        return ResponseEntity
                .created(linkTo(TopicController.class)
                        .slash(id).toUri())
                .body(topicResourceAssembler.toResource(updatedTopic));
    }


    @DeleteMapping(value = "/{id}", produces = { "application/hal+json" })
    public ResponseEntity<Topic> deleteTopic(@PathVariable Long id) {
        if(!topicRepository.existsById(id)) {
            throw new TopicNotFoundException(id);
        }

        topicRepository.deleteById(id);
        return ResponseEntity.noContent()
                .build();
    }
}
