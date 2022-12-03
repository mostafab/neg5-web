package neg5.service.filters;

import static spark.Spark.afterAfter;
import static spark.Spark.before;

import com.google.inject.Inject;
import neg5.monitoring.api.MonitoringContext;
import neg5.userData.CurrentUserContext;

public class MonitoringContextFilter implements RequestFilter {

    private final MonitoringContext monitoringContext;
    private final CurrentUserContext currentUserContext;

    @Inject
    public MonitoringContextFilter(
            MonitoringContext monitoringContext, CurrentUserContext currentUserContext) {
        this.monitoringContext = monitoringContext;
        this.currentUserContext = currentUserContext;
    }

    @Override
    public void registerFilter() {
        before(
                (request, response) -> {
                    monitoringContext.put("request_uri", request.uri());
                    monitoringContext.put("request_method", request.requestMethod());
                    currentUserContext
                            .getUserData()
                            .ifPresent(
                                    userData ->
                                            monitoringContext.put(
                                                    "current_user", userData.getUsername()));
                });
        afterAfter((request, response) -> monitoringContext.clear());
    }
}
