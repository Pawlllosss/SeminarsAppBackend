package pl.oczadly.spring.topics.voting.entity.dto.response;

import java.util.List;

public class CourseVotesResponseDTO {

    private List<VoteDTO> votes;

    public List<VoteDTO> getVotes() {
        return votes;
    }

    public void setVotes(List<VoteDTO> votes) {
        this.votes = votes;
    }
}
