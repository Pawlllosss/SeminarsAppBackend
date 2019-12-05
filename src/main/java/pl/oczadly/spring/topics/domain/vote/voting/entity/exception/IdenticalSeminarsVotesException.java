package pl.oczadly.spring.topics.domain.vote.voting.entity.exception;

public class IdenticalSeminarsVotesException extends IllegalArgumentException {

    public IdenticalSeminarsVotesException() {
        super("You can't vote more than once for the same seminar");
    }
}
