package pl.oczadly.spring.topics.domain.topic.boundary;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.oczadly.spring.topics.domain.seminar.boundary.SeminarRestController;
import pl.oczadly.spring.topics.domain.seminar.entity.dto.SeminarDTO;
import pl.oczadly.spring.topics.domain.topic.entity.Topic;
import pl.oczadly.spring.topics.domain.topic.entity.TopicDTO;

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
        Link seminarsLink = linkTo(methodOn(SeminarRestController.class).getSeminarByTopicId(topicId))
                .withRel("seminars");
        Link addSeminarLink = linkTo(methodOn(SeminarRestController.class).createSeminar(new SeminarDTO(), topicId))
                .withRel("createSeminar");

        return new Resource<>(topic, selfLink, updateLink, deleteLink, seminarsLink, addSeminarLink);
    }
}
