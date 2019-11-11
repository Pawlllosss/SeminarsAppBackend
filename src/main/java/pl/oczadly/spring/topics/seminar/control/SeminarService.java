package pl.oczadly.spring.topics.seminar.control;

import pl.oczadly.spring.topics.seminar.entity.Seminar;
import pl.oczadly.spring.topics.seminar.entity.SeminarDTO;

import java.util.List;

public interface SeminarService {

    Seminar getSeminarById(Long id);

    List<Seminar> getSeminarsByTopicId(Long topicId);

    Seminar createSeminar(SeminarDTO seminarDTO, Long topicId);

    Seminar updateSeminar(SeminarDTO seminarDTO, Long id);

    void deleteSeminar(Long id);
}
