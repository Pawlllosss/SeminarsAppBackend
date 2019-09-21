package pl.oczadly.spring.topics.course.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.oczadly.spring.topics.course.control.CourseRepository;
import pl.oczadly.spring.topics.course.entity.Course;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CourseRepositoryTest {

    private static final String COURSE1_NAME = "Programowanie niskopoziomowe";
    private static final String COURSE2_NAME = "Inżynieria oprogramowania";
    private static final String COURSE3_NAME = "Sieci komputerowe";
    private static final Course COURSE1 = new Course(COURSE1_NAME);
    private static final Course COURSE2 = new Course(COURSE2_NAME);
    private static final Course COURSE3 = new Course(COURSE3_NAME);

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CourseRepository courseRepository;

    @AfterEach
    public void tearDown() {
        entityManager.clear();
    }

    @Test
    public void whenFindAllShouldReturnAllCourses() {
        List<Course> courses = List.of(COURSE1, COURSE2, COURSE3);
        final int expectedNumberOfCourses = courses.size();
        courses.forEach(entityManager::persistAndFlush);

        List<Course> coursesFromRepository = courseRepository.findAll();
        assertThat(coursesFromRepository).hasSize(expectedNumberOfCourses);
        assertThat(coursesFromRepository).extracting(Course::getName)
                .containsOnly(COURSE1_NAME, COURSE2_NAME, COURSE3_NAME);
    }

    @Test
    public void whenInvalidIdThenReturnEmptyResult() {
        Optional<Course> courseFromRepository = courseRepository.findById(-1L);
        assertThat(courseFromRepository).isEmpty();
    }

    @Test
    public void whenCourseSavedThenItShouldBeRetrievable() {
        Course persistedCourse1 = courseRepository.save(COURSE1);
        Optional<Course> course1FromRepository = courseRepository.findById(persistedCourse1.getId());

        assertThat(course1FromRepository).isNotEmpty();
        assertThat(course1FromRepository.get()).isEqualToComparingFieldByField(persistedCourse1);
    }

}