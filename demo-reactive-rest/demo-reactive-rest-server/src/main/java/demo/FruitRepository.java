package demo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface FruitRepository extends ReactiveMongoRepository<Fruit, String> {

    Flux<Fruit> findByName(String name);
}
