package pl.oczadly.spring.topics.voting.entity.exception;

public class UserNotAuthorizedForCourseVoteException extends IllegalStateException {

    public UserNotAuthorizedForCourseVoteException(Long userId, Long courseId) {
        super("User with id: " + userId + " can't vote for course with id: " + courseId);
    }
}
