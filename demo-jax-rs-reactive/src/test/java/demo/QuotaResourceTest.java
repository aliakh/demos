package demo;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import java.util.List;

import static org.junit.Assert.*;

public class QuotaResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(QuotaResource.class, MetalResource.class, PriceResource.class);
    }

    @Test
    public void testGetQuotas() {
        Wrapper wrapper = target("/quota")
                .request()
                .get(Wrapper.class);

        assertNotNull(wrapper);
        assertTrue(wrapper.getProcessingTime() > 0);
        List<Quota> quotas = wrapper.getQuotas();
        assertNotNull(quotas);
        assertEquals(5, quotas.size());
        assertQuota("Iron", quotas.get(0));
        assertQuota("Copper", quotas.get(1));
        assertQuota("Aluminum", quotas.get(2));
        assertQuota("Zinc", quotas.get(3));
        assertQuota("Tin", quotas.get(4));
    }

    private void assertQuota(String metalName, Quota quota) {
        assertNotNull(quota);
        Metal metal = quota.getMetal();
        assertNotNull(metal);
        assertEquals(metalName, metal.getName());
        Price price = quota.getPrice();
        assertNotNull(price);
        assertTrue(price.getValue() > 0);
    }
}
