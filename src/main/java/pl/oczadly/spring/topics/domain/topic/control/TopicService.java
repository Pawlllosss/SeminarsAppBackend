package pl.oczadly.spring.topics.domain.topic.control;

import pl.oczadly.spring.topics.domain.topic.entity.Topic;
import pl.oczadly.spring.topics.domain.topic.entity.TopicDTO;

import java.util.List;

public interface TopicService {

    List<Topic> getAllTopics();

    Topic getTopicById(Long id);

    List<Topic> getTopicsByCourseId(Long courseId);

    Topic createTopic(TopicDTO topicDTO, Long courseId);

    Topic updateTopic(TopicDTO topicDTO, Long id);

    void deleteTopic(Long id);
}
