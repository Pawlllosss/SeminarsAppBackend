package pl.oczadly.spring.topics.domain.topic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.oczadly.spring.topics.domain.course.entity.Course;

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
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "topics")
@DynamicUpdate
public class Topic {

    @Id
    @GeneratedValue(generator = "topic_generator")
    @SequenceGenerator(
            name = "topic_generator",
            sequenceName =  "topic_sequence"
    )
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Course course;

    public Topic() {
    }

    public Topic(String topicName, String description) {
        this.name = topicName;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Topic topic = (Topic) o;
        return Objects.equals(id, topic.id) &&
                Objects.equals(name, topic.name) &&
                Objects.equals(description, topic.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
