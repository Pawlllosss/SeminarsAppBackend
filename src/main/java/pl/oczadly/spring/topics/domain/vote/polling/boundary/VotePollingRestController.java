package pl.oczadly.spring.topics.domain.vote.polling.boundary;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.oczadly.spring.topics.domain.vote.polling.control.VotePollingService;

@RestController
@RequestMapping("vote/polling")
public class VotePollingRestController {

    private VotePollingService votePollingService;

    public VotePollingRestController(VotePollingService votePollingService) {
        this.votePollingService = votePollingService;
    }

    @GetMapping
    public void pollVotes() {
        votePollingService.pollVotes();
    }
}
