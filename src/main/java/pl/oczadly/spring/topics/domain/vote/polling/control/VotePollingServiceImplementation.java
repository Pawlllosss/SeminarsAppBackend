package pl.oczadly.spring.topics.domain.vote.polling.control;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.oczadly.spring.topics.domain.course.control.CourseService;
import pl.oczadly.spring.topics.domain.course.entity.Course;
import pl.oczadly.spring.topics.domain.role.entity.CourseVoterRole;
import pl.oczadly.spring.topics.domain.role.entity.Role;
import pl.oczadly.spring.topics.domain.seminar.control.SeminarService;
import pl.oczadly.spring.topics.domain.seminar.entity.Seminar;
import pl.oczadly.spring.topics.domain.topic.control.TopicService;
import pl.oczadly.spring.topics.domain.topic.entity.Topic;
import pl.oczadly.spring.topics.domain.user.management.control.UserService;
import pl.oczadly.spring.topics.domain.user.management.entity.User;
import pl.oczadly.spring.topics.domain.vote.polling.entity.UserVotes;
import pl.oczadly.spring.topics.domain.vote.voting.control.VoteService;
import pl.oczadly.spring.topics.domain.vote.voting.entity.CourseVotes;
import pl.oczadly.spring.topics.domain.vote.voting.entity.Vote;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VotePollingServiceImplementation implements VotePollingService {

    private static final Random RANDOM = new Random();

    private VoteService voteService;

    private CourseService courseService;
    private TopicService topicService;
    private SeminarService seminarService;
    private UserService userService;

    public VotePollingServiceImplementation(VoteService voteService, CourseService courseService,
                                            TopicService topicService, SeminarService seminarService,
                                            UserService userService) {
        this.voteService = voteService;
        this.courseService = courseService;
        this.topicService = topicService;
        this.seminarService = seminarService;
        this.userService = userService;
    }

    @Override
    @Transactional
    @Scheduled(cron = "1 * * * * *")
    public void pollVotes() {
        List<Course> courses = getCoursesForVotePolling();
        courses.forEach(this::assignSeminarsForCourseVoters);
    }

    private List<Course> getCoursesForVotePolling() {
        List<Course> courses = courseService.getAllCourses();

        return courses.stream()
                .filter(Course::getActive)
                .filter(this::isAfterVoteEndDate)
                .collect(Collectors.toList());
    }

    private boolean isAfterVoteEndDate(Course course) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime voteEndDate = course.getVoteEndDate();
        return currentDate.isAfter(voteEndDate);
    }

    private void assignSeminarsForCourseVoters(Course course) {
        Set<User> users = getUsersWhoCanVoteForCourse(course);
        Long courseId = course.getId();
        List<UserVotes> userVotes = mapUsersToUserVotes(users, courseId);
        List<Topic> topics = topicService.getTopicsByCourseId(courseId);

        disposeSeminarsForUsers(userVotes, topics);
        course.setActive(false);
        course.setVoteEndDate(LocalDateTime.now().plusMinutes(5));
        courseService.updateCourse(course);
    }

    private void disposeSeminarsForUsers(List<UserVotes> userVotes, List<Topic> topics) {
        while (isPossibleToDisposeVotes(topics, userVotes)) {
            Optional<Topic> topicOptional = findTopicToDispose(userVotes);

            if (!topicOptional.isPresent()) {
                assignRemainingTopicsToUsers(userVotes, topics);
                break;
            }

            Topic topic = topicOptional.get();
            topics.remove(topic);

            List<UserVotes> userVotesToDispose = getUsersThatTopVotedTopic(userVotes, topic);

            UserVotes pickedUserVotes = pickUserForSeminar(userVotesToDispose);
            assignSeminarToUser(pickedUserVotes);
            userVotes.remove(pickedUserVotes);

            //TODO: should remove all votes! Case when more votes on same topic!
            userVotesToDispose.forEach(UserVotes::removeTopVote);
        }
    }

    private void assignRemainingTopicsToUsers(List<UserVotes> userVotes, List<Topic> topics) {
        for (Topic topic : topics) {
            List<Seminar> seminars = seminarService.getSeminarsByTopicId(topic.getId());
            if (seminars.isEmpty()) {
                continue;
            }

            Seminar seminar = seminars.get(0);

            if (userVotes.isEmpty()) {
                break;
            } else {
                UserVotes userVotesToAssign = userVotes.remove(0);
                User userToAssign = userVotesToAssign.getUser();
                userService.assignSeminarToUser(userToAssign, seminar);
            }
        }
    }

    private UserVotes pickUserForSeminar(List<UserVotes> userVotesToDispose) {
        int userVotesNumber = userVotesToDispose.size();
        int pickedUserVotesNumber = RANDOM.nextInt(userVotesNumber);

        return userVotesToDispose.get(pickedUserVotesNumber);
    }

    private void assignSeminarToUser(UserVotes pickedUserVotes) {
        User user = pickedUserVotes.getUser();
        Vote topVote = pickedUserVotes.peekTopVote()
                .orElseThrow(() -> new IllegalStateException("User should have voted"));
        Seminar votedSeminar = topVote.getSeminar();

        userService.assignSeminarToUser(user, votedSeminar);
    }

    private List<UserVotes> getUsersThatTopVotedTopic(List<UserVotes> userVotes, Topic topic) {
        return userVotes.stream()
                .filter(userVote -> hasUserTopVotedTopic(userVote, topic))
                .collect(Collectors.toList());
    }

    private Optional<Topic> findTopicToDispose(List<UserVotes> userVotes) {
        return userVotes.stream()
                .map(UserVotes::peekTopVote)
                .flatMap(Optional::stream)
                .map(Vote::getSeminar)
                .map(Seminar::getTopic)
                .findFirst();
    }

    private boolean isPossibleToDisposeVotes(List<Topic> topics, List<UserVotes> userVotes) {
        return !topics.isEmpty() && !userVotes.isEmpty();
    }

    private List<UserVotes> mapUsersToUserVotes(Set<User> users, Long courseId) {
        return users.stream()
            .map(user -> mapToUserVotes(user, courseId))
            .collect(Collectors.toList());
    }

    private boolean hasUserTopVotedTopic(UserVotes userVotes, Topic topic) {
        Optional<Vote> topVote = userVotes.peekTopVote();
        return topVote.map(vote -> checkIfTopicWasVoted(topic, vote))
                .orElse(false);
    }

    private boolean checkIfTopicWasVoted(Topic topic, Vote vote) {
        Seminar votedSeminar = vote.getSeminar();
        Topic votedTopic = votedSeminar.getTopic();

        return votedTopic.equals(topic);
    }

    private Set<User> getUsersWhoCanVoteForCourse(Course course) {
        CourseVoterRole courseVoterRole = course.getCourseVoterRole();
        Role roleRelatedToCourseVoterRole = courseVoterRole.getRole();
        return roleRelatedToCourseVoterRole.getUsers();
    }

    private UserVotes mapToUserVotes(User user, Long courseId) {
        CourseVotes courseVotes = voteService.getUserVotesForTheCourse(user.getId(), courseId);
        Queue<Vote> votes = new LinkedList<>(courseVotes.getVotes());

        return new UserVotes(user, votes);
    }
}
