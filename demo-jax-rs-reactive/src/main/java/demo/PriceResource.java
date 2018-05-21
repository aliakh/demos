package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Random;

@Path("/price")
public class PriceResource {

    private static final Logger logger = LoggerFactory.getLogger(PriceResource.class);

    @GET
    @Path("/{metal}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrice(@PathParam("metal") String metalName) {
        Price price = new Price(new Random().nextInt(100) + 1);

        logger.info("Get price for {}: started", metalName);
        Delay.millis(100);
        logger.info("Get price for {}: finished", metalName);

        return Response.ok(price).build();
    }
}