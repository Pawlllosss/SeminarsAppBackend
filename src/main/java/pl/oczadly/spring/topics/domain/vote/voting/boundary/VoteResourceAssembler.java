package pl.oczadly.spring.topics.domain.vote.voting.boundary;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.oczadly.spring.topics.domain.seminar.boundary.SeminarDetailResponseDTOMapper;
import pl.oczadly.spring.topics.domain.seminar.entity.dto.SeminarDetailResponseDTO;
import pl.oczadly.spring.topics.domain.vote.voting.entity.CourseVotes;
import pl.oczadly.spring.topics.domain.vote.voting.entity.Vote;
import pl.oczadly.spring.topics.domain.vote.voting.entity.dto.CourseVotesResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VoteResourceAssembler implements ResourceAssembler<CourseVotes, Resource<CourseVotesResponseDTO>> {

    private SeminarDetailResponseDTOMapper seminarDetailResponseDTOMapper;

    public VoteResourceAssembler(SeminarDetailResponseDTOMapper seminarDetailResponseDTOMapper) {
        this.seminarDetailResponseDTOMapper = seminarDetailResponseDTOMapper;
    }

    @Override
    public Resource<CourseVotesResponseDTO> toResource(CourseVotes courseVotes) {
        CourseVotesResponseDTO courseVotesResponseDTO = mapToCourseVotesResponseDTO(courseVotes);

        return new Resource<>(courseVotesResponseDTO);
    }

    private CourseVotesResponseDTO mapToCourseVotesResponseDTO(CourseVotes courseVotes) {
        List<Vote> votes = courseVotes.getVotes();
        List<SeminarDetailResponseDTO> seminars = getSeminarsDetailResponseDTOFromVotes(votes);

        return new CourseVotesResponseDTO(seminars);
    }

    private List<SeminarDetailResponseDTO> getSeminarsDetailResponseDTOFromVotes(List<Vote> votes) {
        return votes.stream()
                .map(Vote::getSeminar)
                .map(seminarDetailResponseDTOMapper::convertSeminarToSeminarDetailResponseDTO)
                .collect(Collectors.toList());
    }
}
