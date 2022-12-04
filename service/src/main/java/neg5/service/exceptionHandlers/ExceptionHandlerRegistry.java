package neg5.service.exceptionHandlers;

import com.google.inject.Inject;
import java.util.List;

public class ExceptionHandlerRegistry {

    private final List<ExceptionHandler> exceptionHandlers;

    @Inject
    public ExceptionHandlerRegistry(List<ExceptionHandler> exceptionHandlers) {
        this.exceptionHandlers = exceptionHandlers;
    }

    public void initExceptionHandlers() {
        exceptionHandlers.forEach(ExceptionHandler::registerHandler);
    }
}
