package neg5.domain.impl.matchValidators;

import javax.annotation.Nonnull;
import neg5.domain.api.FieldValidationErrors;

public interface TournamentMatchValidator {

    /**
     * Run some validation logic against a {@link neg5.domain.api.TournamentMatchDTO} before it is
     * created or updated. Any errors should be collected into a {@link FieldValidationErrors}
     * object. Note that all the implementations that satisfy this interface are run independently
     * of one another, so you can make no assumptions about execution order or the validity of the
     * match DTO received. If you need to validate some inner-nested field, please null-check along
     * the way.
     *
     * @param validationContext the validation context
     * @return a populated errors object if any violations are found. Empty otherwise
     */
    @Nonnull
    FieldValidationErrors getErrors(@Nonnull MatchValidationContext validationContext);
}
