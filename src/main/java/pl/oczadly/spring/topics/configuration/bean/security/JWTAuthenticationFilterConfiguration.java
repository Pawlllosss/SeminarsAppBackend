package pl.oczadly.spring.topics.configuration.bean.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.oczadly.spring.topics.user.authentication.control.token.JWTAuthenticationFilter;

@Configuration
public class JWTAuthenticationFilterConfiguration {

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }
}
