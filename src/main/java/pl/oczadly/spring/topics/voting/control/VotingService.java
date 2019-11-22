package pl.oczadly.spring.topics.voting.control;

import pl.oczadly.spring.topics.voting.entity.CourseVotes;
import pl.oczadly.spring.topics.voting.entity.dto.update.CourseVotesUpdateDTO;

public interface VotingService {

    CourseVotes getUserVotesForTheCourse(Long userId, Long courseId);

    CourseVotes setUserVotesForTheCourse(Long userId, Long courseId, CourseVotesUpdateDTO courseVotesUpdateDTO);
}
