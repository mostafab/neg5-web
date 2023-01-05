package neg5.domain.api;

import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import neg5.domain.impl.entities.compositeIds.TournamentTossupValueId;

public interface TournamentTossupValueApi
        extends DomainObjectApiLayer<TournamentTossupValueDTO, TournamentTossupValueId> {

    void deleteAllFromTournament(String tournamentId);

    Set<TournamentTossupValueDTO> getDefaultTournamentValues();

    List<TournamentTossupValueDTO> updateTournamentTossupValues(
            @Nonnull String tournamentId, @Nonnull List<TournamentTossupValueDTO> tossupValues);
}
