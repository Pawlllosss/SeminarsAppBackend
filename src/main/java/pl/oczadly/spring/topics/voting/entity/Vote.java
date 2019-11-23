package pl.oczadly.spring.topics.voting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.oczadly.spring.topics.seminar.entity.Seminar;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(generator = "vote_generator")
    @SequenceGenerator(
            name = "vote_generator",
            sequenceName =  "vote_sequence"
    )
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seminar_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Seminar seminar;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "course_votes_id")
    @JsonIgnore
    private CourseVotes courseVotes;

    @NotNull
    private int priority;

    public Vote() {
    }

    public Vote(Seminar seminar, @NotBlank int priority) {
        this.seminar = seminar;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Seminar getSeminar() {
        return seminar;
    }

    public void setSeminar(Seminar seminar) {
        this.seminar = seminar;
    }

    public CourseVotes getCourseVotes() {
        return courseVotes;
    }

    public void setCourseVotes(CourseVotes courseVotes) {
        this.courseVotes = courseVotes;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
