package pl.oczadly.spring.topics.voting.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.oczadly.spring.topics.course.control.CourseService;
import pl.oczadly.spring.topics.course.entity.Course;
import pl.oczadly.spring.topics.seminar.control.SeminarService;
import pl.oczadly.spring.topics.seminar.entity.Seminar;
import pl.oczadly.spring.topics.user.management.control.UserService;
import pl.oczadly.spring.topics.user.management.entity.User;
import pl.oczadly.spring.topics.voting.entity.CourseVotes;
import pl.oczadly.spring.topics.voting.entity.Vote;
import pl.oczadly.spring.topics.voting.entity.dto.update.CourseVotesUpdateDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VotingServiceImplementation implements VotingService {

    private VotingValidatorService votingValidatorService;
    private UserService userService;
    private CourseService courseService;
    private SeminarService seminarService;

    private CourseVotesRepository courseVotesRepository;

    @Override
    public CourseVotes getUserVotesForTheCourse(Long userId, Long courseId) {
        Optional<CourseVotes> courseVotes = courseVotesRepository.findOptionalByUserIdAndCourseId(userId, courseId);
        return courseVotes.orElseGet(() -> createEmptyCourseVotesForUserAndCourse(courseId, userId));
    }

    private CourseVotes createEmptyCourseVotesForUserAndCourse(Long courseId, Long userId) {
        Course course = courseService.getCourseById(courseId);
        User user = userService.getUserById(userId);

        return new CourseVotes(course, user);
    }

    @Override
    public CourseVotes setUserVotesForTheCourse(Long userId, Long courseId, CourseVotesUpdateDTO courseVotesUpdateDTO) {
        votingValidatorService.validateCourseVotesDTO(userId, courseId, courseVotesUpdateDTO);

        CourseVotes courseVotes = getOrCreateCourseVotes(userId, courseId);

        List<Long> seminarsId = courseVotesUpdateDTO.getSeminarsId();
        List<Vote> votes = createVotesBasedOnSeminarsIdOrder(seminarsId);
        courseVotes.setVotes(votes);

        return courseVotesRepository.save(courseVotes);
    }

    private CourseVotes getOrCreateCourseVotes(Long userId, Long courseId) {
        Optional<CourseVotes> courseVotes = courseVotesRepository.findOptionalByUserIdAndCourseId(userId, courseId);
        return courseVotes.orElseGet(() -> createCourseVotes(courseId, userId));
    }

    private CourseVotes createCourseVotes(Long courseId, Long userId) {
        User user = userService.getUserById(userId);
        Course course = courseService.getCourseById(courseId);

        return new CourseVotes(course, user);
    }

    private List<Vote> createVotesBasedOnSeminarsIdOrder(List<Long> seminarsId) {
        int numberOfSeminars = seminarsId.size();
        List<Vote> votes = new ArrayList<>(numberOfSeminars);
        int priority = 1;

        for (Long seminarId : seminarsId) {
            Seminar seminar = seminarService.getSeminarById(seminarId);
            Vote vote = new Vote(seminar, priority);

            votes.add(vote);
            ++priority;
        }

        return votes;
    }

    @Autowired
    public void setVotingValidatorService(VotingValidatorService votingValidatorService) {
        this.votingValidatorService = votingValidatorService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setSeminarService(SeminarService seminarService) {
        this.seminarService = seminarService;
    }

    @Autowired
    public void setCourseVotesRepository(CourseVotesRepository courseVotesRepository) {
        this.courseVotesRepository = courseVotesRepository;
    }
}
