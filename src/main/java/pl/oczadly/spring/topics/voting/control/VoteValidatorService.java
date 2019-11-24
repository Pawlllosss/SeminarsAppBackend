package pl.oczadly.spring.topics.voting.control;

import pl.oczadly.spring.topics.voting.entity.dto.CourseVotesUpdateDTO;

public interface VoteValidatorService {

    void validateCourseVotesDTO(Long userId, Long courseId, CourseVotesUpdateDTO courseVotesUpdateDTO);
}
