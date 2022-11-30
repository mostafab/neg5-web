package neg5.domain.impl.matchValidators;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.TournamentMatchDTO;

public interface TournamentMatchValidator {

    @Nullable
    FieldValidationErrors getErrors(
            @Nonnull List<TournamentMatchDTO> allMatches, @Nonnull TournamentMatchDTO subject);
}
