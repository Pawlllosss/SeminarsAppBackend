package pl.oczadly.spring.topics.voting.boundary;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.oczadly.spring.topics.seminar.entity.Seminar;
import pl.oczadly.spring.topics.voting.entity.CourseVotes;
import pl.oczadly.spring.topics.voting.entity.Vote;
import pl.oczadly.spring.topics.voting.entity.dto.CourseVotesDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VoteResourceAssembler implements ResourceAssembler<CourseVotes, Resource<CourseVotesDTO>> {

    @Override
    public Resource<CourseVotesDTO> toResource(CourseVotes courseVotes) {
        CourseVotesDTO courseVotesDTO = mapToCourseVotesDTO(courseVotes);

        return new Resource<>(courseVotesDTO);
    }

    private CourseVotesDTO mapToCourseVotesDTO(CourseVotes courseVotes) {
        List<Vote> votes = courseVotes.getVotes();
        List<Long> seminarsId = getSeminarsIdFromVotes(votes);

        return new CourseVotesDTO(seminarsId);
    }

    private List<Long> getSeminarsIdFromVotes(List<Vote> votes) {
        return votes.stream()
                .map(Vote::getSeminar)
                .map(Seminar::getId)
                .collect(Collectors.toList());
    }
}
