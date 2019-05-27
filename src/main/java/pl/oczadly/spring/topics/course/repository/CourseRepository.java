package pl.oczadly.spring.topics.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.oczadly.spring.topics.course.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
