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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    public SeminarDetailResponseDTO() {
    }

    public SeminarDetailResponseDTO(@NotBlank Long id, @NotBlank String topicName, @NotBlank LocalDateTime date) {
        this.id = id;
        this.topicName = topicName;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
