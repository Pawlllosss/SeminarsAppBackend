package pl.oczadly.spring.topics.course.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

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

    public Course() {
    }

    public Course(@NotBlank String name) {
        this.name = name;
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
}


