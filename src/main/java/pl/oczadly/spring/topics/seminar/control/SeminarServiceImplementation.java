package pl.oczadly.spring.topics.seminar.control;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.oczadly.spring.topics.seminar.entity.Seminar;
import pl.oczadly.spring.topics.seminar.entity.SeminarDTO;
import pl.oczadly.spring.topics.seminar.entity.SeminarNotFoundException;
import pl.oczadly.spring.topics.topic.control.TopicService;
import pl.oczadly.spring.topics.topic.entity.Topic;

import java.util.List;

@Service
public class SeminarServiceImplementation implements SeminarService {

    private SeminarRepository seminarRepository;

    private TopicService topicService;

    private ModelMapper mapper;

    @Override
    public Seminar getSeminarById(Long id) {
        return seminarRepository.findById(id).orElseThrow(() -> new SeminarNotFoundException(id));
    }

    @Override
    public List<Seminar> getSeminarsByTopicId(Long topicId) {
        return seminarRepository.findByTopicId(topicId);
    }

    @Override
    public Seminar createSeminar(SeminarDTO seminarDTO, Long topicId) {
        Seminar seminar = mapper.map(seminarDTO, Seminar.class);
        Topic topic = topicService.getTopicById(topicId);
        seminar.setTopic(topic);

        return seminarRepository.save(seminar);
    }

    @Override
    public Seminar updateSeminar(SeminarDTO seminarDTO, Long id) {
        Seminar seminarToUpdate = getSeminarById(id);

        seminarToUpdate.setDate(seminarDTO.getDate());

        return seminarRepository.save(seminarToUpdate);
    }

    @Override
    public void deleteSeminar(Long id) {
        if (!seminarRepository.existsById(id)) {
            throw new SeminarNotFoundException(id);
        }

        seminarRepository.deleteById(id);
    }

    @Autowired
    public void setSeminarRepository(SeminarRepository seminarRepository) {
        this.seminarRepository = seminarRepository;
    }

    @Autowired
    public void setTopicService(TopicService topicService) {
        this.topicService = topicService;
    }

    @Autowired
    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }
}
