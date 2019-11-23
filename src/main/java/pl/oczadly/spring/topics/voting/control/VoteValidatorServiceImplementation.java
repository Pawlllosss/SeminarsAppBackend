package pl.oczadly.spring.topics.voting.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.oczadly.spring.topics.course.control.CourseService;
import pl.oczadly.spring.topics.voting.entity.dto.CourseVotesDTO;
import pl.oczadly.spring.topics.voting.entity.exception.IdenticalSeminarsVotesException;
import pl.oczadly.spring.topics.voting.entity.exception.IllegalNumberOfVotesException;
import pl.oczadly.spring.topics.voting.entity.exception.UserNotAuthorizedForCourseVoteException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class VoteValidatorServiceImplementation implements VoteValidatorService {

    private static final int MAXIMUM_VOTES = 3;

    private CourseService courseService;

    @Override
    public void validateCourseVotesDTO(Long userId, Long courseId, CourseVotesDTO courseVotesDTO) {
        if (!courseService.isCourseAvailableForUserId(userId, courseId)) {
            throw new UserNotAuthorizedForCourseVoteException(userId, courseId);
        }

        if (!checkIfIdenticalSeminarsExist(courseVotesDTO)) {
            throw new IdenticalSeminarsVotesException();
        }

        if (checkIfExceedsMaximumVotes(courseVotesDTO)) {
            throw new IllegalNumberOfVotesException();
        }
    }

    private boolean checkIfIdenticalSeminarsExist(CourseVotesDTO courseVotesDTO) {
        List<Long> seminarsId = courseVotesDTO.getSeminarsId();
        int numberOfSeminars = seminarsId.size();

        Set<Long> uniqueSeminars = new HashSet<>(seminarsId);
        int numberOfUniqueSeminars = uniqueSeminars.size();

        return numberOfSeminars == numberOfUniqueSeminars;
    }

    private boolean checkIfExceedsMaximumVotes(CourseVotesDTO courseVotesDTO) {
        List<Long> seminarsId = courseVotesDTO.getSeminarsId();
        int numberOfSeminars = seminarsId.size();

        return numberOfSeminars > MAXIMUM_VOTES;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
}
