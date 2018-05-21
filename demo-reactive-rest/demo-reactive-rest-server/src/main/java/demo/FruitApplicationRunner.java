package demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
class FruitApplicationRunner implements ApplicationRunner {

    private final FruitRepository fruitRepository;

    FruitApplicationRunner(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.fruitRepository
                .deleteAll()
                .thenMany(
                        Flux.just(
                                "apple|Malus pumila",
                                "orange|Citrus sinensis",
                                "pear|Pyrus communis"
                        )
                )
                .map(text -> text.split("\\|"))
                .map(tuple -> new Fruit(null, tuple[0], tuple[1]))
                .flatMap(this.fruitRepository::save)
                .thenMany(this.fruitRepository.findAll())
                .subscribe(System.out::println);
    }
}
