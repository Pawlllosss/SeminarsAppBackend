package pl.oczadly.spring.topics.domain.vote.voting.control;

import pl.oczadly.spring.topics.domain.vote.voting.entity.dto.CourseVotesUpdateDTO;

public interface VoteValidatorService {

    void validateCourseVotesDTO(Long userId, Long courseId, CourseVotesUpdateDTO courseVotesUpdateDTO);
}
