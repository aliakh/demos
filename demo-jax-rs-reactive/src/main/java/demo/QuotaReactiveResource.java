package demo;

import org.glassfish.jersey.server.Uri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Path("/quota/reactive")
public class QuotaReactiveResource {

    private static final Logger logger = LoggerFactory.getLogger(QuotaReactiveResource.class);

    @Uri("metal")
    private WebTarget metalTarget;

    @Uri("price/{metal}")
    private WebTarget priceTarget;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void getQuota(@Suspended AsyncResponse response) {
        logger.info("Get quota: started");
        long startTime = System.currentTimeMillis();

        metalTarget.request()
                .rx()
                .get(new GenericType<List<Metal>>() {
                }).thenCompose(metals -> {
            List<CompletableFuture<Quota>> quotaFutures = metals
                    .stream()
                    .map(metal -> priceTarget
                            .resolveTemplate("metal", metal.getName())
                            .request()
                            .rx()
                            .get(Price.class)
                            .thenApply(price -> new Quota(metal, price))
                            .toCompletableFuture())
                    .collect(Collectors.toList());
            return CompletableFuture.allOf(quotaFutures.toArray(new CompletableFuture[quotaFutures.size()]))
                    .thenApply(v -> quotaFutures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList()));
        }).whenComplete((quotas, throwable) -> {
            Wrapper wrapper = new Wrapper(System.currentTimeMillis() - startTime, quotas);
            logger.info("Get quota: finished");
            response.resume(wrapper);
        });
    }
}