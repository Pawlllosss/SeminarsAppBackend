package pl.oczadly.spring.topics.voting.entity.exception;

public class IllegalNumberOfVotesException extends IllegalArgumentException {

    public IllegalNumberOfVotesException() {
        super("You can vote for three courses at most");
    }
}
