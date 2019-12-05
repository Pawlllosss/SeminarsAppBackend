package pl.oczadly.spring.topics.domain.vote.voting.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.oczadly.spring.topics.domain.user.authentication.boundary.annotation.CurrentUser;
import pl.oczadly.spring.topics.domain.user.authentication.entity.UserAuthenticationDetails;
import pl.oczadly.spring.topics.domain.vote.voting.control.VoteService;
import pl.oczadly.spring.topics.domain.vote.voting.entity.CourseVotes;
import pl.oczadly.spring.topics.domain.vote.voting.entity.dto.CourseVotesResponseDTO;
import pl.oczadly.spring.topics.domain.vote.voting.entity.dto.CourseVotesUpdateDTO;

@RestController
@RequestMapping(value = "vote", produces = { "application/hal+json" })
public class VoteRestController {

    private VoteService voteService;

    private VoteResourceAssembler voteResourceAssembler;

    @GetMapping("/course/{courseId}")
    public Resource<CourseVotesResponseDTO> getUserVotesForTheCourse(@CurrentUser UserAuthenticationDetails userAuthenticationDetails,
                                                                     @PathVariable Long courseId) {
        Long userId = userAuthenticationDetails.getId();
        CourseVotes courseVotes = voteService.getUserVotesForTheCourse(userId, courseId);

        return voteResourceAssembler.toResource(courseVotes);
    }

    @PutMapping("/course/{courseId}")
    public Resource<CourseVotesResponseDTO>  setUserVotesForTheCourse(@CurrentUser UserAuthenticationDetails userAuthenticationDetails,
                                                                    @PathVariable Long courseId, @RequestBody CourseVotesUpdateDTO courseVotesUpdateDTO) {
        Long userId = userAuthenticationDetails.getId();
        CourseVotes courseVotes = voteService.setUserVotesForTheCourse(userId, courseId, courseVotesUpdateDTO);

        return voteResourceAssembler.toResource(courseVotes);
    }

    @Autowired
    public void setVoteService(VoteService voteService) {
        this.voteService = voteService;
    }

    @Autowired
    public void setVoteResourceAssembler(VoteResourceAssembler voteResourceAssembler) {
        this.voteResourceAssembler = voteResourceAssembler;
    }
}
