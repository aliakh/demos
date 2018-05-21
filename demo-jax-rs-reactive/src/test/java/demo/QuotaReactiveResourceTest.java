package demo;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;

public class QuotaReactiveResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(QuotaReactiveResource.class, MetalResource.class, PriceResource.class);
    }

    @Test
    public void testGetQuotas() throws InterruptedException, ExecutionException, TimeoutException {
        Wrapper wrapper = target("/quota/reactive")
                .request()
                .rx()
                .get(Wrapper.class)
                .toCompletableFuture()
                .get(10, TimeUnit.SECONDS);

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
