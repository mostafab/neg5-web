package neg5.validation;

import org.neg5.FieldValidationErrors;

public class ObjectValidationException extends RuntimeException {

    private final FieldValidationErrors errors;

    public ObjectValidationException(FieldValidationErrors errors) {
        super("Field Validation Errors: " + errors.toString());
        this.errors = errors;
    }

    public FieldValidationErrors getErrors() {
        return errors;
    }
}
