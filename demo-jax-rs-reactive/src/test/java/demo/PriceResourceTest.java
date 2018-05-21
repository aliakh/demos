package demo;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;

import static org.junit.Assert.assertTrue;

public class PriceResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(PriceResource.class);
    }

    @Test
    public void testGetPrice() {
        Price price = target("/price/{metal}")
                .resolveTemplate("metal", "Iron")
                .request()
                .get(Price.class);

        assertTrue(price.getValue() >= 1);
        assertTrue(price.getValue() <= 100);
    }
}