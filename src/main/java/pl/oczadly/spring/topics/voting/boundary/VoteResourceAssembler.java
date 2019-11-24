package pl.oczadly.spring.topics.voting.boundary;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.oczadly.spring.topics.seminar.entity.Seminar;
import pl.oczadly.spring.topics.seminar.entity.SeminarDetailResponseDTO;
import pl.oczadly.spring.topics.voting.entity.CourseVotes;
import pl.oczadly.spring.topics.voting.entity.Vote;
import pl.oczadly.spring.topics.voting.entity.dto.CourseVotesResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VoteResourceAssembler implements ResourceAssembler<CourseVotes, Resource<CourseVotesResponseDTO>> {

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
                .map(this::convertSeminarToSeminarDetailResponseDTO)
                .collect(Collectors.toList());
    }

    private SeminarDetailResponseDTO convertSeminarToSeminarDetailResponseDTO(Seminar seminar) {
        Long id = seminar.getId();
        String topicName = seminar.getTopic().getName();
        LocalDateTime date = seminar.getDate();
        return new SeminarDetailResponseDTO(id, topicName, date);
    }
}
