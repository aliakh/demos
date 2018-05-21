package demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleVerticle extends AbstractVerticle {

    private static final ObjectMapper MAPPER = Json.mapper;

    @Autowired
    private ArticleService articleService;

    @Override
    public void start() throws Exception {
        super.start();
        vertx.eventBus()
                .<String>consumer(Address.FIND_ALL.name())
                .handler(findAll());
    }

    private Handler<Message<String>> findAll() {
        return event -> vertx.<String>executeBlocking(future -> {
            try {
                future.complete(MAPPER.writeValueAsString(articleService.findAll()));
            } catch (JsonProcessingException e) {
                future.fail(e);
            }
        }, result -> {
            if (result.succeeded()) {
                event.reply(result.result());
            } else {
                event.reply(result.cause().toString());
            }
        });
    }
}
