package demo;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class MetalQuotasApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(MetalResource.class);
        classes.add(PriceResource.class);
        classes.add(QuotaResource.class);
        classes.add(QuotaReactiveResource.class);
        return classes;
    }
}