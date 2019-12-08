package pl.oczadly.spring.topics.domain.vote.polling.entity;

import pl.oczadly.spring.topics.domain.seminar.entity.Seminar;
import pl.oczadly.spring.topics.domain.topic.entity.Topic;
import pl.oczadly.spring.topics.domain.user.management.entity.User;
import pl.oczadly.spring.topics.domain.vote.voting.entity.Vote;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserVotes {

    private User user;
    private List<Vote> votes;

    public UserVotes(User user, List<Vote> votes) {
        this.user = user;
        this.votes = votes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserVotes userVotes = (UserVotes) o;

        return Objects.equals(user, userVotes.user) &&
                Objects.equals(votes, userVotes.votes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, votes);
    }

    public Optional<Vote> peekTopVote() {
        int lastElementIndex = votes.size() - 1;
        Optional<Vote> topVote;

        if (lastElementIndex < 0) {
            topVote = Optional.empty();
        } else {
            topVote = Optional.of(votes.get(lastElementIndex));
        }

        return topVote;
    }


    public User getUser() {
        return user;
    }


    public void removeVotesForTopic(Topic topic) {
        List<Vote> votesWithoutTopic = votes.stream()
                .filter(vote -> !isVoteForSameTopic(vote, topic))
                .collect(Collectors.toList());
        this.votes = votesWithoutTopic;
    }

    private boolean isVoteForSameTopic(Vote vote, Topic topic) {
        Seminar votedSeminar = vote.getSeminar();
        Topic votedTopic = votedSeminar.getTopic();
        return votedTopic.equals(topic);
    }
}
