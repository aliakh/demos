package demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
class FruitRestConfiguration {

    @Bean
    RouterFunction<?> routes(FruitRepository fruitRepository) {
        return
            route(GET("/fruits"),
                request -> ok().body(fruitRepository.findAll(), Fruit.class))
            .andRoute(GET("/fruits/{name}"),
                request -> ok().body(fruitRepository.findByName(request.pathVariable("name")), Fruit.class));
    }
}
