package pl.oczadly.spring.topics.domain.course.control;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.oczadly.spring.topics.domain.course.entity.Course;
import pl.oczadly.spring.topics.domain.course.entity.dto.CourseDTO;
import pl.oczadly.spring.topics.domain.course.entity.exception.CourseNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplementationTest {

    private static final String COURSE1_NAME = "Programowanie niskopoziomowe";
    private static final String COURSE2_NAME = "Inżynieria oprogramowania";
    private static final String COURSE3_NAME = "Sieci komputerowe";
    private static final Long COURSE1_ID = 1L;
    private static final Long NOT_EXISTING_ID = 111L;
    private static final LocalDateTime COURSE_END_DATE = LocalDateTime.now().plusYears(1);
    private static final Course COURSE1 = Course.builder()
                                            .name(COURSE1_NAME)
                                            .isActive(true)
                                            .voteEndDate(COURSE_END_DATE)
                                            .build();
    private static final Course COURSE2 = Course.builder()
                                            .name(COURSE2_NAME)
                                            .isActive(true)
                                            .voteEndDate(COURSE_END_DATE)
                                            .build();
    private static final Course COURSE3 = Course.builder()
                                            .name(COURSE3_NAME)
                                            .isActive(true)
                                            .voteEndDate(COURSE_END_DATE)
                                            .build();

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImplementation courseService;

    @Test
    public void whenGetAllCoursesThenShouldReturnAllCourses() {
        mockFindAllUsers();

        List<Course> coursesFromService = courseService.getAllCourses();
        final int expectedCoursesSize = 3;
        verifyCourseRepositoryFindAllCalledOnce();
        assertThat(coursesFromService).hasSize(expectedCoursesSize);
        assertThat(coursesFromService).extracting(Course::getName)
                .containsOnly(COURSE1_NAME, COURSE2_NAME, COURSE3_NAME);
    }

    private void mockFindAllUsers() {
        final List<Course> allCourses = List.of(COURSE1, COURSE2, COURSE3);
        given(courseRepository.findAll()).willReturn(allCourses);
    }

    @Test
    public void whenGetCourseByExistingIdThenReturnCourse() {
        COURSE1.setId(COURSE1_ID);
        mockFindById(COURSE1_ID, COURSE1);

        Course courseFromService = courseService.getCourseById(COURSE1_ID);
        verifyCourseRepositoryFindByIdCalledOnce(COURSE1_ID);
        assertThat(courseFromService.getId()).isEqualTo(COURSE1_ID);
        assertThat(courseFromService.getName()).isEqualTo(COURSE1_NAME);
    }

    private void mockFindById(Long courseId, Course course) {
        given(courseRepository.findById(courseId)).willReturn(Optional.of(course));
    }

    @Test
    public void whenGetCourseByNotExistingIdThenThrowCourseNotFoundException() {
        assertThrows(CourseNotFoundException.class, () -> courseService.getCourseById(NOT_EXISTING_ID));
    }

    @Test
    public void whenUpdatingNotExistingCourseThenThrowCourseNotFoundException() {
        assertThrows(CourseNotFoundException.class, () -> courseService.updateCourse(new CourseDTO(), NOT_EXISTING_ID));
    }

    private void verifyCourseRepositoryFindByIdCalledOnce(Long calledId) {
        verify(courseRepository, times(1)).findById(calledId);
    }

    private void verifyCourseRepositoryFindAllCalledOnce() {
        verify(courseRepository, times(1)).findAll();
    }
}