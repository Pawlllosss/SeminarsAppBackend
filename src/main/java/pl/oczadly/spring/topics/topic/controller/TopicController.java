package pl.oczadly.spring.topics.topic.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    private TopicResourceAssembler topicResourceAssembler;

    public TopicController(TopicRepository topicRepository, TopicResourceAssembler topicResourceAssembler) {
        this.topicRepository = topicRepository;
        this.topicResourceAssembler = topicResourceAssembler;
    }

    @GetMapping(produces = { "application/hal+json" })
    Resources<Resource<Topic>> getAll() {
        List<Resource<Topic>> topics = topicRepository.findAll().stream()
                .map(topicResourceAssembler::toResource)
                .collect(Collectors.toList());

        Link link = linkTo(TopicController.class).withSelfRel();
        return new Resources<>(topics, link);
    }

    @GetMapping(value = "/{id}", produces = { "application/hal+json" })
    Resource<Topic> getById(@PathVariable Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException(id));

        return topicResourceAssembler.toResource(topic);
    }

    @GetMapping(value = "/course/{id}", produces = { "application/hal+json" })
    Resources<Resource<Topic>> getByCourseId(@PathVariable Long courseId) {
        List<Resource<Topic>> topics = topicRepository.findByCourseId(courseId).stream()
                .map(topicResourceAssembler::toResource)
                .collect(Collectors.toList());

        Link link = linkTo(TopicController.class).withSelfRel();
        return new Resources<>(topics, link);
    }

    @PostMapping("/")
    ResponseEntity<Resource<Topic>> addTopic(@RequestBody Topic topic) {
        Topic persistedTopic = topicRepository.save(topic);

        return ResponseEntity
                .created(linkTo(TopicController.class)
                        .slash(persistedTopic.getId()).toUri())
                .body(topicResourceAssembler.toResource(persistedTopic));
    }
}
