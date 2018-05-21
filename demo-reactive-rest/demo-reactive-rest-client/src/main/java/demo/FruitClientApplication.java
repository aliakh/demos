package demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class FruitClientApplication {

    @Bean
    WebClient client(@Value("http://localhost:8080") String url) {
        return WebClient.builder().baseUrl(url).build();
    }

    @Bean
    ApplicationRunner run(WebClient client) {
        return args -> client
                .get()
                .uri("/fruits")
                .retrieve()
                .bodyToFlux(Fruit.class)
                .subscribe(System.out::println);
    }

    public static void main(String[] args) {
        SpringApplication.run(FruitClientApplication.class, args);
    }
}
