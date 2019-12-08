package pl.oczadly.spring.topics.domain.seminar.boundary;

import org.springframework.stereotype.Component;
import pl.oczadly.spring.topics.domain.course.entity.Course;
import pl.oczadly.spring.topics.domain.seminar.entity.Seminar;
import pl.oczadly.spring.topics.domain.seminar.entity.dto.SeminarDetailResponseDTO;
import pl.oczadly.spring.topics.domain.topic.entity.Topic;

import java.time.LocalDateTime;

@Component
public class SeminarDetailResponseDTOMapper {

    public SeminarDetailResponseDTO convertSeminarToSeminarDetailResponseDTO(Seminar seminar) {
        Long id = seminar.getId();
        Topic topic = seminar.getTopic();
        String topicName = topic.getName();
        Course course = topic.getCourse();
        String courseName = course.getName();
        LocalDateTime date = seminar.getDate();
        return new SeminarDetailResponseDTO(id, topicName, courseName, date);
    }
}
