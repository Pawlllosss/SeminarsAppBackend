package pl.oczadly.spring.topics.voting.entity.exception;

public class IdenticalSeminarsVotesException extends IllegalArgumentException {

    public IdenticalSeminarsVotesException() {
        super("You can't vote more than once for the same seminar");
    }
}
