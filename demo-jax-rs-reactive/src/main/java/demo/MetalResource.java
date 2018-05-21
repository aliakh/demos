package demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/metal")
public class MetalResource {

    private static final Logger logger = LoggerFactory.getLogger(MetalResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMetals() {
        List<Metal> metals = Stream.of("Iron", "Copper", "Aluminum", "Zinc", "Tin")
                .map(Metal::new)
                .collect(Collectors.toList());

        logger.info("Get metals: started");
        Delay.millis(100);
        logger.info("Get metals: finished");

        return Response
                .ok(new GenericEntity<List<Metal>>(metals) {
                })
                .build();
    }
}
