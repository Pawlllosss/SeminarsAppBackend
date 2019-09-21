package pl.oczadly.spring.topics.topic.control;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.oczadly.spring.topics.course.entity.Course;
import pl.oczadly.spring.topics.course.entity.CourseNotFoundException;
import pl.oczadly.spring.topics.course.control.CourseRepository;
import pl.oczadly.spring.topics.topic.entity.Topic;
import pl.oczadly.spring.topics.topic.entity.TopicDTO;
import pl.oczadly.spring.topics.topic.entity.TopicNotFoundException;

import java.util.List;

@Service
public class TopicServiceImplementation implements TopicService {

    private TopicRepository topicRepository;
    private CourseRepository courseRepository;

    private ModelMapper mapper;

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @Override
    public Topic getTopicById(Long id) {
        return topicRepository.findById(id).orElseThrow(() -> new TopicNotFoundException(id));
    }

    @Override
    public List<Topic> getTopicsByCourseId(Long courseId) {
        return topicRepository.findByCourseId(courseId);
    }

    @Override
    public Topic createTopic(TopicDTO topicDTO, Long courseId) {
        Topic topic = mapper.map(topicDTO, Topic.class);
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));
        topic.setCourse(course);
        return topicRepository.save(topic);
    }

    @Override
    public Topic updateTopic(TopicDTO topicDTO, Long id) {
        Topic topicToUpdate = topicRepository.findById(id).orElseThrow(() -> new TopicNotFoundException(id));

        topicToUpdate.setName(topicDTO.getName());
        topicToUpdate.setDescription(topicDTO.getDescription());

        return topicRepository.save(topicToUpdate);
    }

    @Override
    public void deleteTopic(Long id) {
        if(!topicRepository.existsById(id)) {
            throw new TopicNotFoundException(id);
        }

        topicRepository.deleteById(id);
    }

    @Autowired
    public void setTopicRepository(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }
}
