package pl.oczadly.spring.topics.domain.course.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class CourseDTO {

    @NotBlank
    private String name;

    @NotBlank
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime voteEndDate;

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
}
