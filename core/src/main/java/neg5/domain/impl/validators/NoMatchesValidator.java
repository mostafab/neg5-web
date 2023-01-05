package neg5.domain.impl.validators;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Set;
import javax.annotation.Nonnull;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.TournamentMatchApi;
import neg5.validation.ObjectValidationException;

@Singleton
public class NoMatchesValidator {

    private final TournamentMatchApi matchApi;

    @Inject
    public NoMatchesValidator(TournamentMatchApi matchApi) {
        this.matchApi = matchApi;
    }

    public void throwIfAnyMatchesExist(@Nonnull String tournamentId, String message) {
        Set<String> matchIds = matchApi.getMatchIdsByTournament(tournamentId);
        if (!matchIds.isEmpty()) {
            throw new ObjectValidationException(
                    new FieldValidationErrors().add("matches", message));
        }
    }
}
