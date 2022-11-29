package neg5.domain.impl.matchValidators;

import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.TournamentMatchDTO;

import javax.annotation.Nonnull;
import java.util.List;

public interface EnhancedMatchValidator {

    FieldValidationErrors getErrors(@Nonnull List<TournamentMatchDTO> allMatches, @Nonnull TournamentMatchDTO subject);
}
