package neg5.service.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;

@Singleton
public class ControllerRegistry {

    private final List<BaseController> controllers;

    @Inject
    public ControllerRegistry(List<BaseController> controllers) {
        this.controllers = controllers;
    }

    public void initControllers() {
        controllers.forEach(BaseController::registerRoutes);
    }
}
