package pl.oczadly.spring.topics.voting.control;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.oczadly.spring.topics.voting.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
