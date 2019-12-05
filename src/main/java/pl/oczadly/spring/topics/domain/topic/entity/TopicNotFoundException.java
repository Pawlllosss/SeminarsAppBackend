package pl.oczadly.spring.topics.domain.topic.entity;

import pl.oczadly.spring.topics.application.exception.NotFoundException;

public class TopicNotFoundException extends NotFoundException {
    public TopicNotFoundException(Long id) {
        super("Topic not found: " + id);
    }
}
