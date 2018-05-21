package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@Component
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public void run(String... args) throws Exception {
        IntStream.range(0, 10)
          .forEach(count -> this.articleRepository.save(new Article(new Random().nextLong(), UUID.randomUUID()
          .toString())));
    }
}
