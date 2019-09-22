package pl.oczadly.spring.topics.role.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.oczadly.spring.topics.course.entity.Course;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "course_voter_roles")
public class CourseVoterRole {

    @Id
    @GeneratedValue(generator = "course_voter_role_generator")
    @SequenceGenerator(
            name = "course_voter_role_generator",
            sequenceName = "course_voter_role_sequence"
    )
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @OneToOne(mappedBy = "courseVoterRole")
    private Course course;

    public CourseVoterRole() {
    }

    public CourseVoterRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
