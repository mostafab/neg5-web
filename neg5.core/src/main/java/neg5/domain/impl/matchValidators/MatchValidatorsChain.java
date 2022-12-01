package neg5.domain.impl.matchValidators;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import java.util.List;
import javax.annotation.Nonnull;
import neg5.domain.api.FieldValidationErrors;

public class MatchValidatorsChain {

    private final List<TournamentMatchValidator> validators;

    @Inject
    public MatchValidatorsChain(
            BasicMatchValidator basicMatchValidator,
            TeamTotalTossupsValidator teamTotalTossupsValidator,
            SingleMatchPerRoundValidator singleMatchPerRoundValidator,
            PlayerAnswersValidator playerAnswersValidator) {
        validators =
                ImmutableList.of(
                        basicMatchValidator,
                        teamTotalTossupsValidator,
                        singleMatchPerRoundValidator,
                        playerAnswersValidator);
    }

    public FieldValidationErrors getErrorsInSequence(
            @Nonnull MatchValidationContext validationContext) {
        FieldValidationErrors errors = new FieldValidationErrors();
        for (TournamentMatchValidator validator : validators) {
            FieldValidationErrors localErrors = validator.getErrors(validationContext);
            localErrors.getErrors().forEach(errors::add);
        }
        return errors;
    }
}
