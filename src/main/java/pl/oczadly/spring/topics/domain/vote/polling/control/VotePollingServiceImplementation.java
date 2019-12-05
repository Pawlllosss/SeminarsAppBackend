package pl.oczadly.spring.topics.domain.vote.polling.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.oczadly.spring.topics.domain.course.control.CourseService;
import pl.oczadly.spring.topics.domain.course.entity.Course;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VotePollingServiceImplementation implements VotePollingService {

    private CourseService courseService;

    @Override
    @Scheduled(cron = "0 * * * * *")
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
        return course.getVoteEndDate().isAfter(currentDate);
    }

    private void assignSeminarsForCourseVoters(Course course) {
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
}
