package neg5.validation;

import java.util.Collection;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import neg5.domain.api.FieldValidationErrors;

public class FieldValidation {

    public static void requireNotNull(
            @Nonnull FieldValidationErrors errors,
            @Nullable Object subject,
            @Nonnull String field) {
        if (subject == null) {
            errors.add(field, String.format("%s is a required field.", field));
        }
    }

    public static void requireNonEmpty(
            @Nonnull FieldValidationErrors errors,
            @Nullable String subject,
            @Nonnull String field) {
        if (subject == null || subject.isEmpty()) {
            errors.add(field, String.format("%s must not be empty.", field));
        }
    }

    public static void requireNonEmpty(
            @Nonnull FieldValidationErrors errors,
            @Nullable Collection<?> subject,
            @Nonnull String field) {
        if (subject == null || subject.isEmpty()) {
            errors.add(field, String.format("%s must not be empty.", field));
        }
    }

    @SafeVarargs
    public static void requireConditionsInSequence(
            @Nonnull FieldValidationErrors errors,
            @Nonnull Consumer<FieldValidationErrors>... validations) {
        int startingSize = errors.size();
        for (Consumer<FieldValidationErrors> consumer : validations) {
            consumer.accept(errors);
            if (errors.size() != startingSize) {
                return;
            }
        }
    }

    public static void requireCondition(
            @Nonnull FieldValidationErrors errors,
            @Nonnull Boolean condition,
            @Nonnull String field,
            @Nonnull String message) {
        if (!condition) {
            errors.add(field, message);
        }
    }

    public static void requireCustomValidation(
            @Nonnull FieldValidationErrors errors, @Nonnull Runnable validator) {
        try {
            validator.run();
        } catch (ObjectValidationException e) {
            e.getErrors()
                    .getErrors()
                    .forEach(err -> errors.add(err.getField(), err.getMessage()));
        }
    }
}
