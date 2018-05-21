package demo;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MetalResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(MetalResource.class);
    }

    @Test
    public void testGetMetals() {
        List<Metal> metals = target("/metal")
                .request()
                .get(new GenericType<List<Metal>>() {
                });

        assertNotNull(metals);
        assertEquals(5, metals.size());
        assertMetal("Iron", metals.get(0));
        assertMetal("Copper", metals.get(1));
        assertMetal("Aluminum", metals.get(2));
        assertMetal("Zinc", metals.get(3));
        assertMetal("Tin", metals.get(4));
    }

    private void assertMetal(String metalName, Metal metal) {
        assertNotNull(metal);
        assertEquals(metalName, metal.getName());
    }
}
