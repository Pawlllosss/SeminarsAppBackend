package pl.oczadly.spring.topics.domain.vote.voting.entity.dto;

import pl.oczadly.spring.topics.domain.seminar.entity.dto.SeminarDetailResponseDTO;

import java.util.List;

public class CourseVotesResponseDTO {

    private List<SeminarDetailResponseDTO> seminars;

    public CourseVotesResponseDTO() {
    }

    public CourseVotesResponseDTO(List<SeminarDetailResponseDTO> seminars) {
        this.seminars = seminars;
    }

    public List<SeminarDetailResponseDTO> getSeminars() {
        return seminars;
    }

    public void setSeminars(List<SeminarDetailResponseDTO> seminars) {
        this.seminars = seminars;
    }
}
