package demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

@Component
public class ServerVerticle extends AbstractVerticle {

    private void getAllArticlesHandler(RoutingContext routingContext) {
        vertx.eventBus()
                .<String>send(Address.FIND_ALL.name(), "", result -> {
                    if (result.succeeded()) {
                        routingContext.response()
                                .putHeader("Content-Type", "application/json")
                                .setStatusCode(200)
                                .end(result.result().body());
                    } else {
                        routingContext.response()
                                .setStatusCode(500)
                                .end();
                    }
                });
    }

    @Override
    public void start() throws Exception {
        super.start();

        Router router = Router.router(vertx);
        router.get("/api/articles")
                .handler(this::getAllArticlesHandler);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(8080);
    }
}
