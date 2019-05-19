package pl.oczadly.spring.topics.topic.entity;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.oczadly.spring.topics.topic.controller.TopicController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


@Component
public class TopicResourceAssembler implements ResourceAssembler<Topic, Resource<Topic>> {

    @Override
    public Resource<Topic> toResource(Topic topic) {
        Long topicId = topic.getTopicId();
        Link selfLink = linkTo(TopicController.class)
                .slash(topicId).withSelfRel();

        return new Resource<>(topic, selfLink);
    }
}
