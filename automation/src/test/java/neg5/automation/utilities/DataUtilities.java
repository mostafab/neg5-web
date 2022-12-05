package neg5.automation.utilities;

import com.github.javafaker.Faker;

public class DataUtilities {

    private static final Faker FAKER = new Faker();

    public static Faker faker() {
        return FAKER;
    }
}
