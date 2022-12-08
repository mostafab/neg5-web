package neg5.validation;

public class ValidationUtilities {

    public static boolean namesAreTheSame(String first, String second) {
        return first.trim().equalsIgnoreCase(second.trim());
    }
}
