package pl.oczadly.spring.topics.domain.seminar.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class SeminarDetailResponseDTO {

    @NotBlank
    private Long id;

    @NotBlank
    private String topicName;

    @NotBlank
    private String courseName;

    @NotBlank
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    public SeminarDetailResponseDTO() {
    }

    public SeminarDetailResponseDTO(@NotBlank Long id, @NotBlank String topicName,
                                    @NotBlank String courseName, @NotBlank LocalDateTime date) {
        this.id = id;
        this.topicName = topicName;
        this.courseName = courseName;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
