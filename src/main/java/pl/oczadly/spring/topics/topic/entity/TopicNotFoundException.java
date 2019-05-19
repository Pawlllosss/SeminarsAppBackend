package pl.oczadly.spring.topics.topic.entity;

public class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException(Long id) {
        super("Topic not found: " + id);
    }
}
