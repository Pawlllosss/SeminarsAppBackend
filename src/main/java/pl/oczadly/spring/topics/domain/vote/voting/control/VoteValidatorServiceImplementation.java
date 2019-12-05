package pl.oczadly.spring.topics.domain.vote.voting.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.oczadly.spring.topics.domain.course.control.CourseService;
import pl.oczadly.spring.topics.domain.vote.voting.entity.dto.CourseVotesUpdateDTO;
import pl.oczadly.spring.topics.domain.vote.voting.entity.exception.IdenticalSeminarsVotesException;
import pl.oczadly.spring.topics.domain.vote.voting.entity.exception.IllegalNumberOfVotesException;
import pl.oczadly.spring.topics.domain.vote.voting.entity.exception.UserNotAuthorizedForCourseVoteException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class VoteValidatorServiceImplementation implements VoteValidatorService {

    private static final int MAXIMUM_VOTES = 3;

    private CourseService courseService;

    @Override
    public void validateCourseVotesDTO(Long userId, Long courseId, CourseVotesUpdateDTO courseVotesUpdateDTO) {
        if (!courseService.isCourseAvailableForUserId(userId, courseId)) {
            throw new UserNotAuthorizedForCourseVoteException(userId, courseId);
        }

        if (!checkIfIdenticalSeminarsExist(courseVotesUpdateDTO)) {
            throw new IdenticalSeminarsVotesException();
        }

        if (checkIfExceedsMaximumVotes(courseVotesUpdateDTO)) {
            throw new IllegalNumberOfVotesException();
        }
    }

    private boolean checkIfIdenticalSeminarsExist(CourseVotesUpdateDTO courseVotesUpdateDTO) {
        List<Long> seminarsId = courseVotesUpdateDTO.getSeminarsId();
        int numberOfSeminars = seminarsId.size();

        Set<Long> uniqueSeminars = new HashSet<>(seminarsId);
        int numberOfUniqueSeminars = uniqueSeminars.size();

        return numberOfSeminars == numberOfUniqueSeminars;
    }

    private boolean checkIfExceedsMaximumVotes(CourseVotesUpdateDTO courseVotesUpdateDTO) {
        List<Long> seminarsId = courseVotesUpdateDTO.getSeminarsId();
        int numberOfSeminars = seminarsId.size();

        return numberOfSeminars > MAXIMUM_VOTES;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
}
