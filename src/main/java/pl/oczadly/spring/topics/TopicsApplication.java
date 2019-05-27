package pl.oczadly.spring.topics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TopicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TopicsApplication.class, args);
	}
}
