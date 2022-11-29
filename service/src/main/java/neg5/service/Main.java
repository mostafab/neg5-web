package neg5.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import neg5.service.guice.Neg5WebModule;

public class Main {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new Neg5WebModule());
        Neg5App app = injector.getInstance(Neg5App.class);
        app.init();
    }
}
