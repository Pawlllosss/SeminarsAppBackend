package pl.oczadly.spring.topics.domain.topic.boundary;

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
import pl.oczadly.spring.topics.domain.topic.control.TopicService;
import pl.oczadly.spring.topics.domain.topic.entity.Topic;
import pl.oczadly.spring.topics.domain.topic.entity.TopicDTO;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/topic", produces = { "application/hal+json" })
public class TopicRestController {

    private TopicService topicService;

    private TopicResourceAssembler topicResourceAssembler;

    @GetMapping
    public Resources<Resource<Topic>> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        List<Resource<Topic>> topicsResponse = topics.stream()
                .map(topicResourceAssembler::toResource)
                .collect(Collectors.toList());

        Link selfLink = linkTo(TopicRestController.class).withSelfRel();
        return new Resources<>(topicsResponse, selfLink);
    }

    @GetMapping("/{id}")
    public Resource<Topic> getTopicById(@PathVariable Long id) {
        Topic topic = topicService.getTopicById(id);
        return topicResourceAssembler.toResource(topic);
    }

    @GetMapping("/course/{courseId}")
    public Resources<Resource<Topic>> getTopicByCourseId(@PathVariable Long courseId) {
        List<Topic> topics = topicService.getTopicsByCourseId(courseId);
        List<Resource<Topic>> topicsResponse = topics.stream()
                .map(topicResourceAssembler::toResource)
                .collect(Collectors.toList());

        Link selfLink = linkTo(TopicRestController.class).withSelfRel();
        return new Resources<>(topicsResponse, selfLink);
    }

    @PostMapping("/course/{courseId}")
    @PreAuthorize("hasAuthority('CRUD_ALL_TOPICS')")
    public ResponseEntity<Resource<Topic>> createTopic(@RequestBody TopicDTO topicDTO, @PathVariable Long courseId) {
        Topic persistedTopic = topicService.createTopic(topicDTO, courseId);
        long persistedTopicId = persistedTopic.getId();

        return ResponseEntity.created(linkTo(methodOn(TopicRestController.class)
                .getTopicById(persistedTopicId))
                .toUri())
                .body(topicResourceAssembler.toResource(persistedTopic));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CRUD_ALL_TOPICS')")
    public ResponseEntity<Resource<Topic>> updateTopic(@RequestBody TopicDTO topicDTO, @PathVariable Long id) {
        Topic updatedTopic = topicService.updateTopic(topicDTO, id);

        return ResponseEntity.created(linkTo(methodOn(TopicRestController.class)
                .getTopicById(id))
                .toUri())
                .body(topicResourceAssembler.toResource(updatedTopic));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CRUD_ALL_TOPICS')")
    public ResponseEntity<Topic> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent()
                .build();
    }

    @Autowired
    public void setTopicService(TopicService topicService) {
        this.topicService = topicService;
    }

    @Autowired
    public void setTopicResourceAssembler(TopicResourceAssembler topicResourceAssembler) {
        this.topicResourceAssembler = topicResourceAssembler;
    }
}
