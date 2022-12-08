package neg5.service.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;

@Singleton
public class RouteRegistry {

    private final List<BaseRoutes> routes;

    @Inject
    public RouteRegistry(List<BaseRoutes> routes) {
        this.routes = routes;
    }

    public void initRoutes() {
        routes.forEach(BaseRoutes::registerRoutes);
    }
}
