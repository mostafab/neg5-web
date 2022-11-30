package neg5.service.filters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class FilterRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterRegistry.class);

    private final Set<RequestFilter> filters;

    @Inject
    public FilterRegistry(Set<RequestFilter> filters) {
        this.filters = filters;
    }

    public void initFilters() {
        filters.forEach(
                filter -> {
                    LOGGER.info("Registering Spark Filter: {}", filter.getClass());
                    filter.registerFilter();
                });
    }
}
