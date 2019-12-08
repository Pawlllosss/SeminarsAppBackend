package pl.oczadly.spring.topics.domain.vote.polling.entity;

import pl.oczadly.spring.topics.domain.seminar.entity.Seminar;
import pl.oczadly.spring.topics.domain.user.management.entity.User;
import pl.oczadly.spring.topics.domain.vote.voting.entity.Vote;

import java.util.Objects;
import java.util.Optional;
import java.util.Queue;

public class UserVotes {

    private User user;
    private Queue<Vote> votes;

    public UserVotes(User user, Queue<Vote> votes) {
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

    public boolean isSeminarAsTopVote(Seminar seminar) {
        Optional<Vote> topVote = Optional.ofNullable(votes.peek());
        return topVote.map(vote -> vote.getSeminar().equals(seminar))
                .orElse(false);
    }

    public Optional<Vote> peekTopVote() {
        return Optional.ofNullable(votes.peek());
    }

    public void removeTopVote() {
        votes.remove();
    }

    public User getUser() {
        return user;
    }

    public Queue<Vote> getVotes() {
        return votes;
    }
}
