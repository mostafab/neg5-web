package neg5.domain.api;

import java.util.ArrayList;
import java.util.List;

public class FieldValidationErrors {

    private final List<FieldValidationError> errors;

    public FieldValidationErrors() {
        errors = new ArrayList<>();
    }

    public FieldValidationErrors add(FieldValidationErrors inner) {
        errors.addAll(inner.getErrors());
        return this;
    }

    public FieldValidationErrors add(FieldValidationError error) {
        return add(error.getField(), error.getMessage());
    }

    public FieldValidationErrors add(String field, String message) {
        FieldValidationError error = new FieldValidationError();
        error.setField(field);
        error.setMessage(message);
        errors.add(error);

        return this;
    }

    public List<FieldValidationError> getErrors() {
        return errors;
    }

    public boolean isEmpty() {
        return errors.isEmpty();
    }

    public int size() {
        return errors.size();
    }

    @Override
    public String toString() {
        return errors.toString();
    }
}
