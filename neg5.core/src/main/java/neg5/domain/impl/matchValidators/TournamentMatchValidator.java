package neg5.domain.impl.matchValidators;

import javax.annotation.Nonnull;
import neg5.domain.api.FieldValidationErrors;

public interface TournamentMatchValidator {

    @Nonnull
    FieldValidationErrors getErrors(@Nonnull MatchValidationContext validationContext);
}
