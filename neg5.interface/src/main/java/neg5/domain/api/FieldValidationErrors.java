package neg5.domain.api;

import java.util.ArrayList;
import java.util.List;

public class FieldValidationErrors {

    private final List<FieldValidationError> errors;

    public FieldValidationErrors() {
        errors = new ArrayList<>();
    }

    public FieldValidationErrors add(FieldValidationError error) {
        return add(error.getFieldName(), error.getMessage());
    }

    public FieldValidationErrors add(String field, String message) {
        FieldValidationError error = new FieldValidationError();
        error.setFieldName(field);
        error.setMessage(message);
        errors.add(error);

        return this;
    }

    public List<FieldValidationError> getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        return errors.toString();
    }
}
