package neg5.dataAccess;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;

public class PersistenceInitializer {

    private final PersistService persistService;

    @Inject
    protected PersistenceInitializer(PersistService persistService) {
        this.persistService = persistService;
    }

    public void start() {
        persistService.start();
    }
}
