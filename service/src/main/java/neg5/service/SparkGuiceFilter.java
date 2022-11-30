package neg5.service;

import com.google.inject.Injector;
import java.util.Arrays;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import spark.servlet.SparkApplication;
import spark.servlet.SparkFilter;

public class SparkGuiceFilter extends SparkFilter {

    private static final String INJECTOR_NAME = Injector.class.getName();

    @Override
    protected SparkApplication[] getApplications(FilterConfig filterConfig)
            throws ServletException {
        SparkApplication[] apps = super.getApplications(filterConfig);

        Injector injector = (Injector) filterConfig.getServletContext().getAttribute(INJECTOR_NAME);

        if (apps != null) {
            Arrays.stream(apps).forEach(injector::injectMembers);
        }
        return apps;
    }
}
