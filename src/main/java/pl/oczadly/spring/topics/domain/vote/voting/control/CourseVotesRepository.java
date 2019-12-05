package pl.oczadly.spring.topics.domain.vote.voting.control;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.oczadly.spring.topics.domain.vote.voting.entity.CourseVotes;

import java.util.Optional;

public interface CourseVotesRepository extends JpaRepository<CourseVotes, Long> {
    Optional<CourseVotes> findOptionalByUserIdAndCourseId(Long userId, Long courseId);
}
