package demo;

import org.glassfish.jersey.server.Uri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/quota")
public class QuotaResource {

    private static final Logger logger = LoggerFactory.getLogger(QuotaResource.class);

    @Uri("metal")
    private WebTarget metalTarget;

    @Uri("price/{metal}")
    private WebTarget priceTarget;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuota() {
        logger.info("Get quota: started");
        long startTime = System.currentTimeMillis();

        List<Quota> quotas = metalTarget
                .request()
                .get(new GenericType<List<Metal>>() {
                }).stream()
                .map(metal -> {
                    Price price = priceTarget
                            .resolveTemplate("metal", metal.getName())
                            .request()
                            .get(Price.class);
                    return new Quota(metal, price);
                }).collect(Collectors.toList());

        Wrapper wrapper = new Wrapper(System.currentTimeMillis() - startTime, quotas);

        logger.info("Get quota: finished");
        return Response.ok(wrapper).build();
    }
}