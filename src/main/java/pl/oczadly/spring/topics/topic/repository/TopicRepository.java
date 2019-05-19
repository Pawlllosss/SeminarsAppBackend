package pl.oczadly.spring.topics.topic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.oczadly.spring.topics.topic.entity.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
