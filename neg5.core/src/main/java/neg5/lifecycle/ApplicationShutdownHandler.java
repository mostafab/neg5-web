package neg5.lifecycle;

import com.google.inject.Singleton;
import java.io.Closeable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class ApplicationShutdownHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationShutdownHandler.class);

    public void registerCloseable(Closeable closeable) {
        LOGGER.info("Registering closeable object {}", closeable.getClass());
        Runtime.getRuntime()
                .addShutdownHook(
                        new Thread(
                                () -> {
                                    try {
                                        LOGGER.warn("Attempting to close {}", closeable.getClass());
                                        closeable.close();
                                    } catch (Exception e) {
                                        LOGGER.error(
                                                "Encountered exception closing object "
                                                        + closeable.getClass(),
                                                e);
                                    }
                                }));
    }
}
