package pl.oczadly.spring.topics.seminar.control;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.oczadly.spring.topics.seminar.entity.Seminar;

import java.util.List;

@Repository
public interface SeminarRepository extends JpaRepository<Seminar, Long> {

    List<Seminar> findByTopicId(Long topicId);
}
