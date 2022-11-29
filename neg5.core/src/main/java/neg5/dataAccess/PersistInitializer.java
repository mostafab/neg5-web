package neg5.dataAccess;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;

public class PersistInitializer {

    private final PersistService persistService;

    @Inject
    protected PersistInitializer(PersistService persistService) {
        this.persistService = persistService;
    }

    public void start() {
        persistService.start();
    }
}
