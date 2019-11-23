package pl.oczadly.spring.topics.voting.control;

import pl.oczadly.spring.topics.voting.entity.dto.CourseVotesDTO;

public interface VoteValidatorService {

    void validateCourseVotesDTO(Long userId, Long courseId, CourseVotesDTO courseVotesDTO);
}
