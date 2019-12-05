package pl.oczadly.spring.topics.domain.vote.voting.control;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.oczadly.spring.topics.domain.vote.voting.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
