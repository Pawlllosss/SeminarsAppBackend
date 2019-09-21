package pl.oczadly.spring.topics.user.authentication.control.token;

import org.springframework.security.core.Authentication;

public interface JWTTokenProvider {

    String generateToken(Authentication authentication);

    Long getUserIdFromToken(String token);

    boolean validateToken(String token);
}
