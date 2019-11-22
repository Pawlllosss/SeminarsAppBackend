package pl.oczadly.spring.topics.voting.entity.dto.update;

import java.util.List;

public class CourseVotesUpdateDTO {

    private List<Long> seminarsId;

    public List<Long> getSeminarsId() {
        return seminarsId;
    }

    public void setSeminarsId(List<Long> seminarsId) {
        this.seminarsId = seminarsId;
    }
}
