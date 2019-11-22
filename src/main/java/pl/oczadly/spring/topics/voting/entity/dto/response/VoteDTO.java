package pl.oczadly.spring.topics.voting.entity.dto.response;

public class VoteDTO {

    private Long seminarId;
    private Long priority;

    public Long getSeminarId() {
        return seminarId;
    }

    public void setSeminarId(Long seminarId) {
        this.seminarId = seminarId;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }
}
