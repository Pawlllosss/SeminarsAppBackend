package pl.oczadly.spring.topics.user.authentication.control.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.oczadly.spring.topics.user.authentication.entity.UserAuthenticationDetails;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTTokenProviderImplementation implements JWTTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JWTTokenProviderImplementation.class);

    private SecretKey key;

    @Value("${app.jwtDurationInMs}")
    private int jwtDurationInMs;

    public JWTTokenProviderImplementation() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    @Override
    public String generateToken(Authentication authentication) {
        UserAuthenticationDetails userAuthenticationDetails = (UserAuthenticationDetails) authentication.getPrincipal();
        Date tokenExpiration = calculateTokenExpiration();

        return Jwts.builder()
                .setSubject(Long.toString(userAuthenticationDetails.getId()))
                .setIssuedAt(new Date())
                .setExpiration(tokenExpiration)
                .signWith(key)
                .compact();
    }

    @Override
    public Long getUserIdFromToken(String token) {
        Claims tokenBody = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(tokenBody.getSubject());
    }

    @Override
    public boolean validateToken(String token) {
        boolean validationResult = true;

        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
        } catch(SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException exception) {
            logger.error(exception.getMessage());
            validationResult = false;
        }

        return validationResult;
    }

    private Date calculateTokenExpiration() {
        Date currentDate = new Date();
        Date tokenExpiration = new Date(currentDate.getTime() + jwtDurationInMs);

        return tokenExpiration;
    }
}
