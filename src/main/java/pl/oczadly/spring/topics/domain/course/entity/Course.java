package pl.oczadly.spring.topics.domain.course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.oczadly.spring.topics.domain.role.entity.CourseVoterRole;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "courses")
public class Course{

    @Id
    @GeneratedValue(generator = "course_generator")
    @SequenceGenerator(
            name = "course_generator",
            sequenceName =  "course_sequence"
    )
    private Long id;

    @NotBlank
    private String name;

    @Future
    private LocalDateTime voteEndDate;

    @NotNull
    private Boolean isActive;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "course_voter_role_id", referencedColumnName = "id")
    private CourseVoterRole courseVoterRole;

    public static Builder builder() {
        return new Builder();
    }

    public Course() {

    }

    public Course(Builder builder) {
        this.name = builder.name;
        this.voteEndDate = builder.voteEndDate;
        this.isActive = builder.isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Course course = (Course) o;

        return Objects.equals(id, course.id) &&
                Objects.equals(name, course.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getVoteEndDate() {
        return voteEndDate;
    }

    public void setVoteEndDate(LocalDateTime voteEndDate) {
        this.voteEndDate = voteEndDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public CourseVoterRole getCourseVoterRole() {
        return courseVoterRole;
    }

    public void setCourseVoterRole(CourseVoterRole courseVoterRole) {
        this.courseVoterRole = courseVoterRole;
    }

    public static class Builder {

        private Long id;
        private String name;
        private LocalDateTime voteEndDate;
        private Boolean isActive;
        private CourseVoterRole courseVoterRole;

        private Builder() {

        }

        public Builder from(Course course) {
            this.id = course.id;
            this.name = course.name;
            this.voteEndDate = course.voteEndDate;
            this.isActive = course.isActive;
            this.courseVoterRole = course.courseVoterRole;

            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder voteEndDate(LocalDateTime voteEndDate) {
            this.voteEndDate = voteEndDate;
            return this;
        }

        public Builder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Builder courseVoteRole(CourseVoterRole courseVoterRole) {
            this.courseVoterRole = courseVoterRole;
            return this;
        }

        public Course build() {
            return new Course(this);
        }
    }
}


