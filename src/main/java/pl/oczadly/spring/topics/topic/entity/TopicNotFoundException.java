package pl.oczadly.spring.topics.topic.entity;

import pl.oczadly.spring.topics.exception.NotFoundException;

public class TopicNotFoundException extends NotFoundException {
    public TopicNotFoundException(Long id) {
        super("Topic not found: " + id);
    }
}
