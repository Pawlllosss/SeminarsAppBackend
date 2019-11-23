package pl.oczadly.spring.topics.voting.entity.dto;

import java.util.List;

public class CourseVotesDTO {

    private List<Long> seminarsId;

    public CourseVotesDTO() {
    }

    public CourseVotesDTO(List<Long> seminarsId) {
        this.seminarsId = seminarsId;
    }

    public List<Long> getSeminarsId() {
        return seminarsId;
    }

    public void setSeminarsId(List<Long> seminarsId) {
        this.seminarsId = seminarsId;
    }
}
