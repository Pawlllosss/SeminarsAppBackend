package pl.oczadly.spring.topics.topic.entity;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.oczadly.spring.topics.topic.controller.TopicController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@Component
public class TopicResourceAssembler implements ResourceAssembler<Topic, Resource<Topic>> {

    @Override
    public Resource<Topic> toResource(Topic topic) {
        Long topicId = topic.getId();
        Link selfLink = linkTo(methodOn(TopicController.class).getById(topicId))
                .withSelfRel();
        Link updateLink = linkTo(methodOn(TopicController.class).updateTopic(topic, topicId))
                .withRel("update");
        Link deleteLink = linkTo(methodOn(TopicController.class).deleteTopic(topicId))
                .withRel("delete");


        return new Resource<>(topic, selfLink, updateLink, deleteLink);
    }
}
