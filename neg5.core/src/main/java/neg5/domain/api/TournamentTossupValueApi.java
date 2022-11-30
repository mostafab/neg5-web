package neg5.domain.api;

import java.util.Set;
import neg5.domain.impl.entities.compositeIds.TournamentTossupValueId;

public interface TournamentTossupValueApi
        extends DomainObjectApiLayer<TournamentTossupValueDTO, TournamentTossupValueId> {

    void deleteAllFromTournament(String tournamentId);

    Set<TournamentTossupValueDTO> getDefaultTournamentValues();
}
