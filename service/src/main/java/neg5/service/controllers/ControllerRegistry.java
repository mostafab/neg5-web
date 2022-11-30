package neg5.service.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Set;

@Singleton
public class ControllerRegistry {

    private final Set<BaseController> controllers;

    @Inject
    public ControllerRegistry(Set<BaseController> controllers) {
        this.controllers = controllers;
    }

    public void initControllers() {
        controllers.forEach(BaseController::registerRoutes);
    }
}
