package pl.oczadly.spring.topics.user.authentication.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTokenProviderImplementation implements JWTTokenProvider {

    //TODO: include this in config
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtDurationInMs}")
    private int jwtDurationInMs;

    @Override
    public String generateToken(Authentication authentication) {

        Date currentDate = new Date();
        Date tokenExpiration = new Date(currentDate.getTime() + jwtDurationInMs);

        return Jwts
    }

    @Override
    public Long getUserIdFromToken(String token) {
        return null;
    }

    @Override
    public boolean validateToken(String token) {
        return false;
    }
}
