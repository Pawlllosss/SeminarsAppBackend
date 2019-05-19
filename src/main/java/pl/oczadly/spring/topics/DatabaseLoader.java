package pl.oczadly.spring.topics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.oczadly.spring.topics.topic.entity.Topic;
import pl.oczadly.spring.topics.topic.repository.TopicRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {
    private final TopicRepository topicRepository;

    @Autowired
    public DatabaseLoader(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        topicRepository.save(new Topic("Bootloadery", "Proces bootowania i tworzenie bootloaderow"));
    }
}
