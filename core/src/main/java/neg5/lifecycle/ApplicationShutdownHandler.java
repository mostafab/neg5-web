package neg5.lifecycle;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class ApplicationShutdownHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationShutdownHandler.class);

    private final List<Closeable> closeables;

    @Inject
    public ApplicationShutdownHandler() {
        this.closeables = new ArrayList<>();

        Runtime.getRuntime().addShutdownHook(new Thread(this::closeAll));
    }

    public void registerCloseable(Closeable closeable) {
        LOGGER.info("Registering closeable object {}", closeable.getClass());
        this.closeables.add(closeable);
    }

    private void closeAll() {
        for (Closeable closeable : closeables) {
            try {
                LOGGER.warn("Attempting to close {}", closeable.getClass());
                closeable.close();
            } catch (Exception e) {
                LOGGER.error("Encountered exception closing object " + closeable.getClass(), e);
            }
        }
    }
}
