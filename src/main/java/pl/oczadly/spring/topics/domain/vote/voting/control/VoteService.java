package pl.oczadly.spring.topics.domain.vote.voting.control;

import pl.oczadly.spring.topics.domain.vote.voting.entity.CourseVotes;
import pl.oczadly.spring.topics.domain.vote.voting.entity.dto.CourseVotesUpdateDTO;

public interface VoteService {

    CourseVotes getUserVotesForTheCourse(Long userId, Long courseId);

    CourseVotes setUserVotesForTheCourse(Long userId, Long courseId, CourseVotesUpdateDTO courseVotesUpdateDTO);
}
