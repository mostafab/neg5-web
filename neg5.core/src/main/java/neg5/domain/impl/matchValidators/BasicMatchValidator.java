package neg5.domain.impl.matchValidators;

import static neg5.validation.FieldValidation.requireCondition;
import static neg5.validation.FieldValidation.requireNonEmpty;
import static neg5.validation.FieldValidation.requireNotNull;

import com.google.inject.Singleton;
import javax.annotation.Nonnull;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.TournamentMatchDTO;

@Singleton
public class BasicMatchValidator implements TournamentMatchValidator {

    @Override
    @Nonnull
    public FieldValidationErrors getErrors(@Nonnull MatchValidationContext validationContext) {
        FieldValidationErrors errors = new FieldValidationErrors();
        TournamentMatchDTO subject = validationContext.getSubject();
        requireNotNull(errors, subject.getTournamentId(), "tournamentId");
        requireNotNull(errors, subject.getRound(), "round");
        requireNonEmpty(errors, subject.getPhases(), "phases");
        requireCondition(
                errors,
                subject.getRound() == null || subject.getRound() > 0,
                "round",
                "Round must be greater than 0");
        requireCondition(
                errors,
                subject.getTossupsHeard() == null || subject.getTossupsHeard() >= 0,
                "tossupsHeard",
                "Tossups Heard must be empty or non-negative");
        return errors;
    }
}
