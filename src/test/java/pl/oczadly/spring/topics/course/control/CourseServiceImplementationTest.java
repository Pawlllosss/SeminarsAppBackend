package pl.oczadly.spring.topics.course.control;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.oczadly.spring.topics.course.entity.Course;
import pl.oczadly.spring.topics.course.repository.CourseNotFoundException;
import pl.oczadly.spring.topics.course.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
public class CourseServiceImplementationTest {

    private static final String COURSE1_NAME = "Programowanie niskopoziomowe";
    private static final String COURSE2_NAME = "Inżynieria oprogramowania";
    private static final String COURSE3_NAME = "Sieci komputerowe";
    private static final Long COURSE1_ID = 1L;
    private static final Long NOT_EXISTING_ID = 111L;
    private static final Course COURSE1 = new Course(COURSE1_NAME);
    private static final Course COURSE2 = new Course(COURSE2_NAME);
    private static final Course COURSE3 = new Course(COURSE3_NAME);

    @TestConfiguration
    static class CourseServiceImplementationTestConfiguration {
        @Bean
        public CourseService courseService() {
            return new CourseServiceImplementation();
        }
    }

    @Autowired
    private CourseService courseService;

    @MockBean
    private CourseRepository courseRepository;

    @Before
    public void setUpMocks() {
        COURSE1.setId(COURSE1_ID);

        final List<Course> allCourses = List.of(COURSE1, COURSE2, COURSE3);

        given(courseRepository.findAll()).willReturn(allCourses);
        given(courseRepository.findById(COURSE1_ID)).willReturn(Optional.of(COURSE1));
    }

    @Test
    public void whenGetAllCoursesThenShouldReturnAllCourses() {
        List<Course> coursesFromService = courseService.getAllCourses();
        final int expectedCoursesSize = 3;
        verifyCourseRepositoryFindAllCalledOnce();
        assertThat(coursesFromService).hasSize(expectedCoursesSize);
        assertThat(coursesFromService).extracting(Course::getName)
                .containsOnly(COURSE1_NAME, COURSE2_NAME, COURSE3_NAME);
    }

    @Test
    public void whenGetCourseByExistingIdThenReturnCourse() {
        Course courseFromService = courseService.getCourseById(COURSE1_ID);
        verifyCourseRepositoryFindByIdCalledOnce(COURSE1_ID);
        assertThat(courseFromService.getId()).isEqualTo(COURSE1_ID);
        assertThat(courseFromService.getName()).isEqualTo(COURSE1_NAME);
    }

    @Test
    public void whenGetCourseByNotExistingIdThenThrowCourseNotFoundException() {
        assertThrows(CourseNotFoundException.class, () -> courseService.getCourseById(NOT_EXISTING_ID));
    }

    @Test
    public void whenUpdatingNotExistingCourseThenThrowCourseNotFoundException() {
        assertThrows(CourseNotFoundException.class, () -> courseService.updateCourse(new Course("test"), NOT_EXISTING_ID));
    }

    private void verifyCourseRepositoryFindByIdCalledOnce(Long calledId) {
        verify(courseRepository, times(1)).findById(calledId);
    }

    private void verifyCourseRepositoryFindAllCalledOnce() {
        verify(courseRepository, times(1)).findAll();
    }
}