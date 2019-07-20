package pl.oczadly.spring.topics.course.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "pl.oczadly.spring.topics.course.repository")
@PropertySource("application.properties")
@EnableTransactionManagement
public class TestingConfig {
}
