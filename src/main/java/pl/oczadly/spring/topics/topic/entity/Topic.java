package pl.oczadly.spring.topics.topic.entity;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Topic extends ResourceSupport {
    private @Id @GeneratedValue Long topicId;
    private String topicName;
    private String description;

    public Topic() {
    }

    public Topic(String topicName, String description) {
        this.topicName = topicName;
        this.description = description;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
