package pl.oczadly.spring.topics.voting.control;

import pl.oczadly.spring.topics.voting.entity.dto.update.CourseVotesUpdateDTO;

public interface VotingValidatorService {

    void validateCourseVotesDTO(Long userId, Long courseId, CourseVotesUpdateDTO courseVotesUpdateDTO);
}
