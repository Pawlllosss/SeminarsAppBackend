package pl.oczadly.spring.topics.topic.boundary;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.oczadly.spring.topics.topic.entity.Topic;
import pl.oczadly.spring.topics.topic.entity.TopicDTO;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@Component
public class TopicResourceAssembler implements ResourceAssembler<Topic, Resource<Topic>> {

    @Override
    public Resource<Topic> toResource(Topic topic) {
        Long topicId = topic.getId();

        Link selfLink = linkTo(methodOn(TopicRestController.class).getTopicById(topicId))
                .withSelfRel();
        Link updateLink = linkTo(methodOn(TopicRestController.class).updateTopic(new TopicDTO(), topicId))
                .withRel("update");
        Link deleteLink = linkTo(methodOn(TopicRestController.class).deleteTopic(topicId))
                .withRel("delete");

        return new Resource<>(topic, selfLink, updateLink, deleteLink);
    }
}
