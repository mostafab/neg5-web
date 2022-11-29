package neg5.domain.impl.matchValidators;

import org.neg5.FieldValidationErrors;
import org.neg5.TournamentMatchDTO;

import javax.annotation.Nonnull;
import java.util.List;

public interface EnhancedMatchValidator {

    FieldValidationErrors getErrors(@Nonnull List<TournamentMatchDTO> allMatches, @Nonnull TournamentMatchDTO subject);
}
