package pl.oczadly.spring.topics.user.authentication.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.oczadly.spring.topics.user.authentication.control.UserAuthenticationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

    private UserAuthenticationService authenticationService;
    private JWTTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = getTokenFromRequest(request);
        token.ifPresent(jwt -> setAuthentication(jwt, request));

        filterChain.doFilter(request, response);
    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = getAuthorizationHeader(request);

        if(!isAuthorizationHeaderValid(authorizationHeader)) {
            return Optional.empty();
        }

        String token = extractTokenFromAuthorizationHeader(authorizationHeader);

        if(!isTokenValid(token)) {
            return Optional.empty();
        }

        return Optional.of(token);
    }

    private String getAuthorizationHeader(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER);
    }

    private boolean isAuthorizationHeaderValid(String authorizationHeader) {
        return containsBearerToken(authorizationHeader);
    }

    private boolean containsBearerToken(String authorizationHeader) {
        return StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(BEARER_TOKEN_PREFIX);
    }

    private boolean isTokenValid(String token) {
        return tokenProvider.validateToken(token);
    }

    private String extractTokenFromAuthorizationHeader(String authorizationHeader) {
        int authorizationHeaderLength = authorizationHeader.length();
        String token = authorizationHeader.substring(BEARER_TOKEN_PREFIX.length(), authorizationHeaderLength);

        return token;
    }

    private void setAuthentication(String token, HttpServletRequest request) {
        Long userId = tokenProvider.getUserIdFromToken(token);
        UserDetails userDetails = authenticationService.loadUserById(userId);

        UsernamePasswordAuthenticationToken tokenForAuthenticationContext = getAuthenticationContextToken(userDetails, request);
        SecurityContextHolder.getContext().setAuthentication(tokenForAuthenticationContext);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationContextToken(UserDetails userDetails, HttpServletRequest request) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        WebAuthenticationDetails authenticationDetails = new WebAuthenticationDetailsSource().buildDetails(request);

        authenticationToken.setDetails(authenticationDetails);
        return authenticationToken;
    }

    @Autowired
    public void setAuthenticationService(UserAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Autowired
    public void setTokenProvider(JWTTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }
}
